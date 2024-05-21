package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Sphere represents a sphere in the 3D space
 * The class is based on the RadialGeometry class
 * */
public class Sphere extends RadialGeometry {
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
        //should be implemented later
        return null;
    }
}
