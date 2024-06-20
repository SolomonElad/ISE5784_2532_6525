package renderer;

import geometries.Intersectable;
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
        List<Intersectable.GeoPoint> intersections  = this.scene.geometries.findGeoIntersections(ray);
        return intersections == null ? this.scene.background : calcColor(ray.findClosestGeoPoint(intersections),ray);
    }

    /**
     * Calculate the color of the intersection point
     *
     * @param point the intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint point,Ray ray) {
        return this.scene.ambientLight.getIntensity()
                .add(point.geometry.getEmission())
                .add(calcLocalEffects(point,ray));
    }

    /**
     * calculate the local effects of the intersection point
     * @param gp the intersection point
     * @param ray the ray that intersects the point
     * @return the color of the intersection point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
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
    return material.kD.scale(Math.abs(nl));
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
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = alignZero(v.dotProduct(r)*-1);
        return material.kS.scale(vr > 0 ? Math.pow(vr, material.shininess) : 0);
    }
}