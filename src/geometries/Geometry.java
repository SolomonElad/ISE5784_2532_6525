package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is the basic interface for all geometric objects
 * in the 3D space
 * the interface has one method getNormal(Point)
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission of the geometry
     */
    protected Color emission = Color.BLACK;

    /**
     * The material of the geometry
     */
    private Material material = new Material();

    /**
     * method to get the normal of the geometry in specific point
     * @param point point on the geometry's surface
     * @return the normal of the geometry in the point
     */
    public abstract Vector getNormal(Point point);

    /**
     * method to get the emission of the geometry
     * @return the emission of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * method to set the emission of the geometry
     * @param emission the emission of the geometry
     * @return the geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * method to get the material of the geometry
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * method to set the material of the geometry
     * @param material the material of the geometry
     * @return the geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
