package geometries;

import primitives.*;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Plane represents a plane in the 3D space
 * The plane is represented by point and vector
 * */
public class Plane implements Geometry {

    private final Point q;
    private final Vector normal;

    /** Constructor for a plane in the 3D space - creates a normalized vector
     * using the cross product of two vectors on the plane
     * @param point1 the first point on the plane
     * @param point2 the second point on the plane
     * @param point3 the third point on the plane
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q = point1;
        this.normal = point1.subtract(point2).crossProduct(point1.subtract(point3)).normalize();
    }

    /** Constructor for a plane in the 3D space
     * @param point the point on the plane
     * @param vector the normal of the plane
     */
    public Plane(Point point, Vector vector) {
        this.q = point;
        this.normal = vector.normalize();
    }

    /**
     * method to get the normal of the plane
     * @return the normal of the plane
     */
    public Vector getNormal() {
        return this.normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return this.normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // if the ray starts on the plane, there is no intersection
        if (q.equals(ray.getHead())) {
            return null;
        }

        // if the ray is parallel to the plane, there is no intersection
        double nv = normal.dotProduct(ray.getDirection());
        // can't divide by zero
        if (isZero(nv)) {
            return null;
        }

        // if the ray is included in the plane, there is no intersection
        double nQMinusHead = alignZero(this.normal.dotProduct(q.subtract(ray.getHead())));
        if (isZero(nQMinusHead)) {
            return null;
        }

        double t = alignZero(nQMinusHead / nv);
        // if the intersection point is behind the ray or in tha head, there is no intersection
        if (t <= 0) {
            return null;
        }

        return List.of(ray.getPoint(t));
    }
}
