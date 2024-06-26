package geometries;

import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class Geometries represents a collection of geometries in the 3D space
 * The class is based on the Intersectable
 */
public class Geometries extends Intersectable {
    final private List<Intersectable> geometries = new LinkedList<Intersectable>();

    /**
     * Default empty Constructor for a collection of geometries in the 3D space
     */
    public Geometries() {}

    /**
     * Constructor for a collection of geometries in the 3D space
     * @param geometries the geometries to add to the collection
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Add geometries to the collection
     * @param geometries the geometries to add to the collection
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : geometries) {
            List<GeoPoint> geometryIntersections = geometry.findGeoIntersections(ray, maxDistance);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<GeoPoint>();
                }
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }
}
