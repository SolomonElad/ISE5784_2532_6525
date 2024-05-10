package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Plane represents a plane in the 3D space
 * The class is based on the Point and Vector classes
 * */
public class Plane {
    private final Point q;
    private final Vector normal;

    /** Constructor for a plane in the 3D space
     * @param point1 the first point on the plane
     * @param point2 the second point on the plane
     * @param point3 the third point on the plane
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q = point1;

        //should be implemented later
        this.normal = null;
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
    public Vector getNormal()
    {
        return this.normal;
    }
}
