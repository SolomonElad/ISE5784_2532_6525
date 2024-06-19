package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

/**
 * Class SpotLight represents a spot light source in the scene
 */
public class SpotLight extends PointLight{

    /**
     * The direction of the light
     */
    private final Vector direction;

    /**
     * Constructor that creates a spot light source with a given intensity, position, and direction
     *
     * @param intensity  The intensity of the spot light source
     * @param position The position of the light
     * @param direction The direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKc(kL);
    }

    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKc(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity().scale(max(0,direction.dotProduct(getL(p))));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
