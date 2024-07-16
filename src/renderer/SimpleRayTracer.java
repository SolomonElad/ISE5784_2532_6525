package renderer;

import com.aparapi.Kernel;
import lighting.LightSource;
import primitives.*;

import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The SimpleRayTracer class is a simple ray tracer that traces rays in a scene
 * and returns the color of the intersection point.
 */
public class SimpleRayTracer extends RayTracerBase {

    //although these values are private, they very important for the ray tracer, so we make Javadoc for them
    /**
     * The number of rays to send for the soft shadow effect
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * The minimum value for the attenuation factor
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * The initial attenuation factor
     */
    private static final double INITIAL_K = 1.0;

    /**
     * Constructor for the SimpleRayTracer class
     *
     * @param scene the scene to trace rays in
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? this.scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Traces multiple rays in the scene and returns the average color of the intersection points
     *
     * @param rays the rays to trace
     * @return the average color of the intersection points
     */
    public Color traceMultipleRays(List<Ray> rays) {
        Color avg = this.scene.background;
        for (Ray ray : rays) {
            avg = avg.add(traceRay(ray));
        }
        return avg.reduce(rays.size());
    }

//    public Color traceMultipleRays(List<Ray> rays) {
//        return rays.parallelStream()
//                .map(this::traceRay)
//                .reduce(Color::add)
//                .map(color -> color.reduce(rays.size()))
//                .orElse(this.scene.background);
//    }

    /**
     * Calculate the color of the intersection point
     *
     * @param gp  the intersection GeoPoint
     * @param ray the ray that intersects the point
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculate the color of the intersection point with the given level of recursion
     * helper function for calcColor(GeoPoint, Ray)
     *
     * @param geoPoint the intersection point
     * @param ray      the ray that intersects the point
     * @param level    the level of the recursion
     * @param k        the attenuation factor
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Construct the reflected ray of the intersection point
     *
     * @param point the intersection point
     * @param v     the vector from the camera to the point
     * @param n     the normal of the geometry
     * @return the reflected ray of the intersection point
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v.subtract(n.scale(alignZero(v.dotProduct(n))).scale(2)), n);
    }

    /**
     * Construct the refracted ray of the intersection point
     *
     * @param point the intersection point
     * @param v     the vector from the camera to the point
     * @param n     the normal of the geometry
     * @return the refracted ray of the intersection point
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * find the closest intersection point of the ray with the scene
     *
     * @param ray the ray to find the intersection point with
     * @return the closest intersection point of the ray with the scene
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * calculate the global effects of the intersection point
     *
     * @param gp    the intersection point
     * @param ray   the ray that intersects the point
     * @param level the level of the recursion
     * @param k     the attenuation factor
     * @return the color of the intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kr = material.kR, kkr = k.product(kr);
        Double3 kt = material.kT, kkt = k.product(kt);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(constructReflectedRay(gp.point, v, n), level - 1, kr, kkr));
        }

        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level - 1, kt, kkt));
        }
        return color;
    }

    /**
     * calculate the global effect of the intersection point
     *
     * @param ray   the ray to find the intersection point with
     * @param level the level of the recursion
     * @param kx    the attenuation factor
     * @param kkx   the attenuation factor multiplied by the material's attenuation factor
     * @return the color of the intersection point
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * calculate the local effects of the intersection point
     *
     * @param gp  the intersection point
     * @param ray the ray that intersects the point
     * @param k   the attenuation factor
     * @return the color of the intersection point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl < 0 ? -nl : nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * calculate the diffusive effect of the intersection point
     *
     * @param material the material of the geometry
     * @param nl       the dot product of the normal and the light vector
     * @return the color of the diffusive effect
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(nl);
    }

    /**
     * calculate the specular effect of the intersection point
     *
     * @param material the material of the geometry
     * @param n        the normal of the geometry
     * @param l        the vector from the light source to the point
     * @param nl       the dot product of the normal and the light vector
     * @param v        the vector from the camera to the point
     * @return the color of the specular effect
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        // VR = v * (l - n * 2 * nl)
        double vr = v.dotProduct(l.subtract(n.scale(nl * 2)));
        return (alignZero(vr) > 0) ? Double3.ZERO : material.kS.scale(Math.pow(-vr, material.shininess));
    }

    /**
     * calculate the transparency of the intersection point
     *
     * @param gp    the intersection point
     * @param light the light source
     * @param l     the vector from the light source to the point
     * @param n     the normal of the geometry
     * @return the transparency of the intersection point
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Ray lightRay = new Ray(gp.point, l.scale(-1), n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        if (intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;

        for (GeoPoint geoPoint : intersections) {
            ktr = ktr.product(geoPoint.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }

    /**
     * check if the point is shaded
     *
     * @param gp          the intersection point
     * @param l           the vector from the light source to the point
     * @param n           the normal of the geometry
     * @param lightSource the light source
     * @return true if the point is shaded, false otherwise
     * @deprecated Use {@link #transparency(GeoPoint, LightSource, Vector, Vector) transparency}
     * because it is more accurate
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        return scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point)) == null;
    }
}