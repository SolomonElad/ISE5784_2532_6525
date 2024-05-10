package geometries;

/**
 * Abstract class RadialGeometry is the basic class for all radial geometric objects
 * in the 3D space
 * the class has one field radius
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * The radius of the radial geometry
     */
    final double radius;

    /**
     * Constructor for a radial geometry
     * gets the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
