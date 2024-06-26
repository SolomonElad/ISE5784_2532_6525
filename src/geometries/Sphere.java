package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;

/**
 * Class Sphere represents a sphere in the 3D space
 * The class is based on the abstract RadialGeometry class
 */
public class Sphere extends RadialGeometry {

    /**
     * the center of the sphere
     */
    private final Point center;

    /**
     * Constructor for a sphere in the 3D space
     *
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (center.equals(ray.getHead())) {
            return List.of(new GeoPoint(this,center.add(ray.getDirection().scale(radius))));
        }

        Vector u = center.subtract(ray.getHead());
        double tm = alignZero(ray.getDirection().dotProduct(u));
        double d = alignZero(sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius)
            return null;

        double th = alignZero(sqrt(radius * radius - d * d));

        double t2 = alignZero(tm + th);
        if (t2 <= 0 || alignZero(t2 - maxDistance) > 0)
            return null; // both t1 and t2 are not positive, because t1 < t2 (always) - no intersections

        double t1 = alignZero(tm - th);
        if (alignZero(t1 - maxDistance) > 0)
            return null;

        // return the points in the correct order - the first point is the closest to the ray's head
        return t1 <= 0 ? List.of(new GeoPoint(this,ray.getPoint(t2)))
                : List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));
    }
}
