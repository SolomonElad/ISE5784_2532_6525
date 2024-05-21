package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is the basic interface for all geometric objects
 * in the 3D space
 * the interface has one method getNormal(Point)
 */
public interface Geometry {
    /**
     * method to get the normal of the geometry in specific point
     * @param point point on the geometry's surface
     * @return the normal of the geometry in the point
     */
    public Vector getNormal(Point point);
}
