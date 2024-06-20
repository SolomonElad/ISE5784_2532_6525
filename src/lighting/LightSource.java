package lighting;

import primitives.*;

/**
 * Abstract class LightSource is the base class for all light sources in the scene
 * that implement the light propagation model
 */
public interface LightSource {

    /**
     * Getter for the intensity of the light
     * @param p the point of the object
     * @return the intensity of the light
     */
    Color getIntensity(Point p);

    /**
     * Getter for the vector from the light source to the point
     * @param p the point of the object
     * @return the vector from the light source to the point
     * NOTE: the vector is normalized
     */
    Vector getL(Point p);
}