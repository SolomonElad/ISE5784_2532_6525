package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light class represents the light that comes from all directions and
 * is reflected equally from all surfaces in the scene. The intensity of the
 * light is constant and is not affected by the direction of the surface.
 * Ambient light is used to simulate the light that is reflected from the
 * environment and is not affected by the direction of the surface.
 */
public class AmbientLight extends Light {

    /**
     * A constant ambient light source that is not affected by the direction of the
     * surface.
     * BLACK background color
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor that creates an ambient light source with a given intensity.
     *
     * @param Ia The intensity of the ambient light source
     * @param Ka The coefficient of the ambient light source (Double3)
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Constructor that creates an ambient light source with a given intensity.
     *
     * @param Ia The intensity of the ambient light source
     * @param Ka The coefficient of the ambient light source (Double)
     */
    public AmbientLight(Color Ia, Double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Constructor that creates an ambient light source with a given intensity and default coefficient of 1.
     *
     * @param Ia The intensity of the ambient light source
     * default Ka = 1.0
     */
    public AmbientLight(Color Ia) {
        super(Ia);
    }

}
