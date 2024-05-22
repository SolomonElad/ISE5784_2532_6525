package geometries;

import primitives.Point;
import primitives.Vector;

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
}
