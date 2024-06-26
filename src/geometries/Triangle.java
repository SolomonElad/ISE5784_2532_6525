package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Class Triangle represents a triangle in the 3D space
 * The class is based on the Polygon class
 */
public class Triangle extends Polygon {

    /**
     * Constructor for a triangle in the 3D space:
     *
     * @param point1 the first point of the triangle
     * @param point2 the second point of the triangle
     * @param point3 the third point of the triangle
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);
    }

    /**
     * alternative implementation of findIntersections method, based on side relativity to edges of the triangle:
     */
//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        // if there are no intersections with the triangle's plane, there are no intersections with the triangle
//        if (plane.findIntersections(ray) == null)
//            return null;
//
//        // calculate the vectors from the ray's head to the vertices of the triangle
//        Vector v1 = vertices.get(0).subtract(ray.getHead());
//        Vector v2 = vertices.get(1).subtract(ray.getHead());
//        Vector v3 = vertices.get(2).subtract(ray.getHead());
//
//        // if there are intersections with the triangle's plane, check if the intersections are inside the triangle
//        Vector n1 = v1.crossProduct(v2).normalize();
//        Vector n2 = v2.crossProduct(v3).normalize();
//        Vector n3 = v3.crossProduct(v1).normalize();
//
//        Vector direction = ray.getDirection();
//        // if we get same sign for all dotProducts, the intersection is inside the triangle
//        if ((alignZero(direction.dotProduct(n1)) > 0 && alignZero(direction.dotProduct(n2)) > 0 && alignZero(direction.dotProduct(n3)) > 0) ||
//            (alignZero(direction.dotProduct(n1)) < 0 && alignZero(direction.dotProduct(n2)) < 0 && alignZero(direction.dotProduct(n3)) < 0))
//            // delegation to plane
//            return plane.findIntersections(ray);
//
//        return null;
//    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // If there is no intersection with the plane containing the triangle, there is no intersection with the triangle
        List<GeoPoint> points = plane.findGeoIntersections(ray, maxDistance);
        if (points == null) {
            return null;
        }

        // the intersection point with the plane - we check if it is inside the triangle via barycentric coordinates
        Point point = points.getFirst().point;

        // defining the vertices of the triangle
        Point a = vertices.get(0);
        Point b = vertices.get(1);
        Point c = vertices.get(2);

        // if the intersection point is a vertex of the triangle, it is not considered an intersection
        if (point.equals(a) || point.equals(b) || point.equals(c)) {
            return null;
        }

        Vector ab, bc, ap, bp, cp, ac;
        double area, alpha, beta, gamma;

        try {
            ab = b.subtract(a); // a->b
            bc = c.subtract(b); // b->c
            ac = c.subtract(a); // a->c
            ap = point.subtract(a); // a->q
            bp = point.subtract(b); // b->q
            cp = point.subtract(c); // c->q
            // Alpha, beta, and gamma are calculated by the ratio between the respective
            // triangles and the entire one.
            area = ab.crossProduct(ac).length();
            alpha = ab.crossProduct(ap).length() / area;
            beta = bc.crossProduct(bp).length() / area;
            gamma = ac.crossProduct(cp).length() / area;
            // Point is inside if all the coordinates are positive

            return (alignZero(alpha) > 0 && alignZero(beta) > 0 && alignZero(gamma) > 0
                    && isZero(gamma + beta + alpha - 1)) ?
                    List.of(new GeoPoint(this,points.getFirst().point)) : null;

            // if we get cross product of two vectors that make Zero vector, the point is not considered inside the triangle
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}