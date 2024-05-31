package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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

    @Override
    public Vector getNormal(Point point) {
        //if point is on same slice as head of axis - create the normalized vector like on a circle
        if (axis.getDirection().dotProduct(point.subtract(axis.getHead())) == 0)
            return point.subtract(axis.getHead()).normalize();
        //else, calculate the center point of the slice of the cylinder that contains point,
        //then create the normalized vector like on a circle
        return point.subtract(axis.getPoint(axis.getDirection().dotProduct(point.subtract(axis.getHead())))).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Not implemented
        return null;
    }
}
