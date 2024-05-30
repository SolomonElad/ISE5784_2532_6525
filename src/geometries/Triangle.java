package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        // if there are no intersections with the triangle's plane, there are no intersections with the triangle
        if (plane.findIntersections(ray) == null)
            return null;

        // calculate the vectors from the ray's head to the vertices of the triangle
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        // if there are intersections with the triangle's plane, check if the intersections are inside the triangle
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        Vector direction = ray.getDirection();
        // if we get same sign for all dotProducts, the intersection is inside the triangle
        if ((alignZero(direction.dotProduct(n1)) > 0 && alignZero(direction.dotProduct(n2)) > 0 && alignZero(direction.dotProduct(n3)) > 0) ||
            (alignZero(direction.dotProduct(n1)) < 0 && alignZero(direction.dotProduct(n2)) < 0 && alignZero(direction.dotProduct(n3)) < 0))
            // delegation to plane
            return plane.findIntersections(ray);

        return null;
    }
}
