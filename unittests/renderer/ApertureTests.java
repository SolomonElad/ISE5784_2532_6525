package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static renderer.Aperture.generateAperturePoints;

/**
 * Testing Aperture class - visualizing the aperture points

 */
class ApertureTests {

    /**
     * Test method for {@link renderer.Aperture#generateAperturePoints(primitives.Point, primitives.Vector, primitives.Vector, double, int)}.
     */
    @Test
    void testGenerateAperturePoints() {
        Point p0 = new Point(0, 0, 0);
        Vector vUp = new Vector(0, 1, 0);
        Vector vRight = new Vector(1, 0, 0);
        double aperture = 3d;
        int multipleRaysNum = 10;

        List<Point> points = generateAperturePoints(p0, vUp, vRight, aperture, multipleRaysNum);

        ImageWriter imageWriter = new ImageWriter("ApertureVisualization" + "# Rays " + multipleRaysNum, 1000, 1000);

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                imageWriter.writePixel(i, j, new Color(0, 0, 0));
            }
        }

        for (Point point : points) {
            int x = (int) ((point.getX() - p0.getX() + aperture) / (2 * aperture) * 1000);
            int y = (int) ((point.getY() - p0.getY() + aperture) / (2 * aperture) * 1000);

            if (x >= 0 && x < 1000 && y >= 0 && y < 1000) {
                imageWriter.writePixel(x, y, new Color(255, 255, 0));
            }
        }

        imageWriter.writeToImage();
    }
}