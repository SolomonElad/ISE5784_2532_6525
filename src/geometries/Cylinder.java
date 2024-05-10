package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Cylinder represents a cylinder in the 3D space
 * The class is based on the Tube class
 * */
public class Cylinder extends Tube {
    final private double height;

    /** Constructor for a cylinder in the 3D space
     * @param axis the axis of the cylinder
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point)
    {
        //should be implemented later
        return super.getNormal(point);
    }
}
