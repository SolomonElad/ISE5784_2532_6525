package geometries;

/**
 * Abstract class RadialGeometry is the basic class for all radial geometries
 * in the 3D space
 * the class has one field - radius
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * The radius of the radial geometry
     */
    final protected double radius;

    /**
     * Constructor for a radial geometry
     * @param radius the geometry's radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
