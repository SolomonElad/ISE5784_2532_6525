package scene;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;

import static java.awt.Color.YELLOW;

/**
 * Test class for SceneBuilderFromXML
 */
class SceneBuilderFromXMLTests {
    /**
     * Cameras builder of the tests
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    // Camera for Elad's scene
    private final Camera.Builder cameraElad = Camera.getBuilder()
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    /**
     * Test method for {@link SceneBuilderFromXML#setFromFile(String)}.
     */
//    @Test
//    void testSetFromFile() {
//
//        camera
//                .setRayTracer(new SimpleRayTracer(SceneBuilderFromXML.setFromFile("xml/renderTestTwoColors.xml")))
//                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//                .build()
//                .renderImage()
//                .printGrid(100, new Color(YELLOW))
//                .writeToImage();
//
//
//        // Elad's scene test file
//        cameraElad
//                .setRayTracer(new SimpleRayTracer(SceneBuilderFromXML.setFromFile("xml/elad.xml")))
//                .setImageWriter(new ImageWriter("xml render test elad", 1000, 1000))
//                .build()
//                .renderImage()
//                .printGrid(100, new Color(YELLOW))
//                .writeToImage();
//    }
}