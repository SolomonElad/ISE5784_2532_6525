package lighting;

import primitives.Color;

/**
 * Abstract class Light is the base class for all light sources in the scene
 */
abstract class Light {
    /**
     * the intensity of the light
     */
    protected Color intensity;

    /**
     * Constructor for the Light class
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter for the intensity of the light
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }


}
