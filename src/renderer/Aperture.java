package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * The Aperture class is a utility class for generating aperture points
 */

public class Aperture {
    private static final double jitterFactor = 3;

    /**
     * Generate aperture points
     *
     * @param p0              the point to generate the aperture points around
     * @param vUp             the vector up
     * @param vRight          the vector right
     * @param aperture        the aperture size
     * @param multipleRaysNum the number of rays to generate
     * @return a list of points
     */
    public static List<Point> generateAperturePoints(Point p0, Vector vUp, Vector vRight, double aperture, int multipleRaysNum) {
        Random random = new Random();
        List<Point> points = new ArrayList<Point>();
        double step = aperture * 2 / (multipleRaysNum + 1);
        double jitter = step / jitterFactor;
        points.add(p0);
        for (double i = -aperture + jitter; i < aperture - jitter; i += step) {
            if (isZero(i)) continue;
            for (double j = -aperture + jitter; j < aperture - jitter; j += step) {
                if (isZero(j)) continue;
                //scale point with jitter
                Point p = p0.add(vRight.scale(i + random.nextDouble() * jitter))
                        .add(vUp.scale(j + random.nextDouble() * jitter));
                //add point to list if it is within the aperture
                if (p0.distance(p) <= aperture) points.add(p);
            }
        }
        return points;
    }
}
