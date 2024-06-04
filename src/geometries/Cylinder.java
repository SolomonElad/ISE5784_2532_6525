package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Class Cylinder represents a cylinder in the 3D space
 * The class is based on the Tube class
 */
public class Cylinder extends Tube {
    final private double height;

    /**
     * Constructor for a cylinder in the 3D space
     *
     * @param axis   the axis of the cylinder
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        //case 1 - point is on first base
        if (point.equals(axis.getHead()) || isZero(axis.getDirection().dotProduct(point.subtract(axis.getHead()))))
            return axis.getDirection().scale(-1);

        //case 2 - point is on second base
        if (point.equals(axis.getPoint(height)) ||
                isZero(axis.getDirection().dotProduct(point.subtract(axis.getPoint(height)))))
            return axis.getDirection();

        //case 3 - point is on the side
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Not implemented
        return null;
    }
}
