package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Class Ray represents a ray in the 3D space
 * The class is based on the Point and Vector classes
 */
public class Ray {
    /* The head of the ray */
    private final Point head;
    /* The direction of the ray */
    private final Vector direction;

    /**
     * Constructor for a ray in the 3D space
     *
     * @param head      the head of the ray
     * @param direction the direction of the ray
     *                  the direction vector is normalized
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray ray)
                && this.head.equals(ray.head)
                && this.direction.equals(ray.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Get the direction of the ray
     *
     * @return the direction of the ray
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Get the head of the ray
     *
     * @return the head of the ray
     */
    public Point getHead() {
        return head;
    }

    /**
     * Calculate a point on the rays line at a distance t from the head
     *
     * @param t the distance from the head
     * @return the point on the rays line at the distance t from the head
     */
    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * Find the closest point to the head of the ray from a list of points
     *
     * @param points the list of points to search in
     * @return the closest point to the head of the ray (Point)
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Find the closest GeoPoint to the head of the ray from a list of GeoPoints
     *
     * @param geoPointList the list of GeoPoints to search in
     * @return the closest GeoPoint to the head of the ray (GeoPoint)
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        if (geoPointList == null || geoPointList.isEmpty()) {
            return null;
        }

        GeoPoint closestGeoPoint = null;
        double minDistance = Double.MAX_VALUE;
        double pointDistance;

        for (var geoPointInList : geoPointList) {
            pointDistance = this.head.distance(geoPointInList.point);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestGeoPoint = geoPointInList;
            }
        }
        return closestGeoPoint;
    }
}
