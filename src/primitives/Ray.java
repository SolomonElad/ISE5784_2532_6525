package primitives;

import java.util.Objects;

import static primitives.Util.isZero;

/** Class Ray represents a ray in the 3D space
 * The class is based on the Point and Vector classes
 * */
public class Ray {
    /* The head of the ray */
    private final Point head;
    /* The direction of the ray */
    private final Vector direction;

    /** Constructor for a ray in the 3D space
     * @param head the head of the ray
     * @param direction the direction of the ray
     * the direction vector is normalized
     * */
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

    /** Get the direction of the ray
     * @return the direction of the ray
     * */
    public Vector getDirection() {
        return direction;
    }

    /** Get the head of the ray
     * @return the head of the ray
     * */
    public Point getHead() {
        return head;
    }

    /** Calculate a point on the rays line at a distance t from the head
     * @param t the distance from the head
     * @return the point on the rays line at the distance t from the head
     * */
    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));
    }
}
