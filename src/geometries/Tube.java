package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Tube represents a tube in the 3D space
 * The class is based on the RadialGeometry class
 * */
public class Tube extends RadialGeometry {
    /** The axis of the tube */
    final protected Ray axis;

    /** Constructor for a tube in the 3D space
     * @param axis the axis of the tube
     * @param radius the radius of the tube
     */
    public Tube(Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }

    public Vector getNormal(Point point)
    {
        //should be implemented later
        return null;
    }
}
