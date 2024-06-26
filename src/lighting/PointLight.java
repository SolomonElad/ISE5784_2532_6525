package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
public class PointLight extends Light implements LightSource {

    /**
     * The position of the light
     */
    protected final Point position;
    /**
     * The attenuation factors of the light
     * kC - The constant attenuation factor
     * kL - The linear attenuation factor
     * kQ - The quadratic attenuation factor
     */

    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Constructor that creates a point light source with a given intensity and position
     *
     * @param intensity The intensity of the point light source
     * @param position  The position of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
    /**
     * setter for the constant attenuation factor of the light
     * @param kC The constant attenuation factor
     * @return The point light source
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for the linear attenuation factors of the light
     * @param kL The linear attenuation factor
     * @return The point light source
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for the quadratic attenuation factors of the light
     * @param kQ The quadratic attenuation factor
     * @return The point light source
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return this.getIntensity().scale(1d / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}