package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface Intersectable is interface for all geometric objects
 * finding the intersections of the object with a ray
 */
public abstract class Intersectable {
    /**
     * method to find the intersections of the object with a ray
     * @param ray the ray to find the intersections with
     * @return list of the intersections points
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }
    /**
     * method to find the GeoIntersections of the object with a ray
     * @param ray the ray to find the intersections with
     * @return list of the intersections points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * method to find the GeoIntersections of the object with a ray
     * @param ray the ray to find the intersections with
     * @return list of the intersections points
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


    /**
     * class GeoPoint is a class to represent a point of intersection
     */
    public static class GeoPoint {
        /**
         * the geometry of the point
         */
        public Geometry geometry;

        /**
         * the point of the intersection
         */
        public Point point;

        /**
         * Constructor for a GeoPoint
         * @param geometry the geometry of the point
         * @param point the point of the intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * override for the equals method
         * @param o the object to compare to
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return o instanceof GeoPoint &&
                    (geometry.equals(((GeoPoint) o).geometry) && point.equals(((GeoPoint) o).point));
        }

        /**
         * override for the toString method
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
