package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class DirectionalLight represents a directional light source in the scene
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * The direction of the light
     */
    private final Vector direction;

    /**
     * Constructor that creates a directional light source with a given intensity and direction
     *
     * @param intensity  The intensity of the directional light source
     * @param direction The direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
