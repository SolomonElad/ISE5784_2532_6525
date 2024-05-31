package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Class Triangle represents a triangle in the 3D space
 * The class is based on the Polygon class
 * */
public class Triangle extends Polygon {

    /** Constructor for a triangle in the 3D space
     * @param point1 the first point of the triangle
     * @param point2 the second point of the triangle
     * @param point3 the third point of the triangle
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);
    }

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
    public List<Point> findIntersections(Ray ray) {
        // If there is no intersection with the plane containing the triangle, there is no intersection with the triangle
        List<Point> points = plane.findIntersections(ray);
        if (points == null) {
            return null;
        }

        // the intersection point with the plane - we check if it is inside the triangle via barycentric coordinates
        Point point = points.getFirst();

        // if the intersection point is a vertex of the triangle, it is not considered an intersection
        if (point.equals(vertices.get(0)) || point.equals(vertices.get(1)) || point.equals(vertices.get(2))) {
            return null;
        }

        // defining the vertices of the triangle
        Point A = vertices.get(0);
        Point B = vertices.get(1);
        Point C = vertices.get(2);

        // calculate the vectors from the first vertex of the triangle to the other vertices e.g(A->B, A->C)
        // and from the first vertex to the intersection point e.g(A->P)
        // u = A -> B
        Vector u = B.subtract(A);
        // v = A -> C
        Vector v = C.subtract(A);
        // w = A -> P
        Vector w = point.subtract(A);

        // we check if the point is between the two vectors we created - if not, there is no point of intersection in the triangle
        // calculate cross products to check orientation
        Vector vCrossW = v.crossProduct(w);
        Vector vCrossU = v.crossProduct(u);
        // if the dot product of the cross products is negative, the point is outside the triangle
        if (vCrossW.dotProduct(vCrossU) < 0) {
            return null;
        }

        // repeat the check for the other vector pairs
        Vector uCrossW = u.crossProduct(w);
        Vector uCrossV = u.crossProduct(v);
        if (uCrossW.dotProduct(uCrossV) < 0) {
            return null;
        }

        // calculate the denominator for barycentric coordinates
        double denominator = alignZero(uCrossV.length());
        if (isZero(denominator))
            return null;

        double x = alignZero(vCrossW.length() / denominator);
        double y = alignZero(uCrossW.length() / denominator);

        // check if the point is inside the triangle using barycentric coordinates
        // x and y express the linear combination of two vectors that produce two sides of the triangle,
        // according to the relationships we will know if the point is in the triangle or not
        if (x > 0 && y > 0 && (x + y) < 1) {
            return points;
        }

        return null;
    }
}
