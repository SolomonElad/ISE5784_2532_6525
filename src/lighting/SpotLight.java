package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Class SpotLight represents a spot light source in the scene
 */
public class SpotLight extends PointLight{

    /**
     * The direction of the light
     */
    private final Vector direction;

    /**
     * The angle of the beam of the spot light
     */
    private double narrowness = 1;

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

    /**
     * Set the factor of the beam of the spot light
     *
     * @param narrowness The factor of the beam of the spot light
     * @return The spot light with the new factor of the beam
     */
    public SpotLight setNarrowBeam(double narrowness) {
        this.narrowness = narrowness;
        return this;
    }

    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
        double dotProduct = alignZero(direction.dotProduct(getL(p)));
        return dotProduct <= 0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(dotProduct, narrowness));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
