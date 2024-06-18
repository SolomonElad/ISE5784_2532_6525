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
     * Scene of the tests
     */
    private final Scene scene = new Scene("XML Test scene");
    private final Scene eladScene = new Scene("XML Test elad");

    /**
     * Cameras builder of the tests
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    // Camera for Elad's scene
    private final Camera.Builder cameraElad = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(eladScene))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    /**
     * Test method for {@link scene.SceneBuilderFromXML#setFromFile(String, Scene)}.
     */
    @Test
    void testSetFromFile() {
        SceneBuilderFromXML.setFromFile("xml/renderTestTwoColors.xml", scene);

        camera
                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();


        // Elad's scene test file
        SceneBuilderFromXML.setFromFile("xml/elad.xml", eladScene);

        cameraElad
                .setImageWriter(new ImageWriter("xml render test elad", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();
    }
}