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
 * */
public class Sphere extends RadialGeometry {

    /** the center of the sphere */
    private final Point center;

    /** Constructor for a sphere in the 3D space
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
    public List<Point> findIntersections(Ray ray) {
        if (center.equals(ray.getHead())) {
            return List.of(center.add(ray.getDirection().scale(radius)));
        }

        Vector u = center.subtract(ray.getHead());
        double tm = alignZero(ray.getDirection().dotProduct(u));
        double d = alignZero(sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius)
            return null;

        double th = alignZero(sqrt(radius * radius - d * d));
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        if (t1 > 0 && t2 > 0) {
            // return the points in the correct order - the first point is the closest to the ray's head
            if (t1 > t2)
                return List.of(ray.getPoint(t1), ray.getPoint(t2));
            return List.of(ray.getPoint(t2), ray.getPoint(t1));
        }

        if (t1 > 0)
            return List.of(ray.getPoint(t1));

        if (t2 > 0)
            return List.of(ray.getPoint(t2));

        return null;
    }
}
