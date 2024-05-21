package primitives;

import java.util.Objects;

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
    public int hashCode() {
        return Objects.hash(head, direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
