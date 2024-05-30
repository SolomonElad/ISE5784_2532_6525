package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface Intersectable is interface for all geometric objects
 * finding the intersections of the object with a ray
 */
public interface Intersectable {
    /**
     * method to find the intersections of the object with a ray
     * @param ray the ray to find the intersections with
     * @return list of the intersections points
     */
    List<Point> findIntersections(Ray ray);
}
