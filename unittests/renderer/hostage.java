package renderer;

import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import scene.Scene;
import geometries.*;
import scene.SceneBuilderFromXML;
import primitives.*;

import static java.awt.Color.*;

public class hostage {

    /**
     * Scene of the tests
     */
    private Scene scene = SceneBuilderFromXML.setFromFile("xml/2478.xml");
    //private Scene scene = new Scene("test");


    /**
     * Camera builder of the tests
     */
    Camera.Builder camera = Camera.getBuilder();

    @Test
    void hostage() {
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));
        scene.setBackground(new Color(212, 198, 158));

        scene.lights.add(
                new SpotLight(new Color(120, 120, 120), new Point(0, 150, 250), new Vector(-1, -1, -1))
                        .setKl(4E-4).setKq(2E-5).setNarrowBeam(0.0000000000000000001)
        );
        scene.lights.add(new DirectionalLight(new Color(200, 200, 200), new Vector(-1.25, -1, -1)));

//        scene.lights.add(
//                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
//                        .setKl(0.0004).setKq(0.0000006));


        scene.geometries.add(
                new Triangle(new Point(200, 200, -8), new Point(-600, 200, -8), new Point(200, -600, -8))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(212, 198, 158))
//                new Sphere(new Point(0, 45, 20), 5d).setEmission(new Color(RED))
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
//                new Sphere(new Point(0, 45, 20), 2d).setEmission(new Color(yellow))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
                );

        camera
                .setLocation(new Point(0, -75, 100))
                .setVpDistance(100)
                .setVpSize(100, 100)
                .setFocusPoint(new Point(0, 0, 0))
                .setRotation(180)
                .setRayTracer(new SimpleRayTracer(scene))
                .setImageWriter(new ImageWriter("Hostages", 1000, 1000))
                .build()
                .renderImage()
                .writeToImage();
    }
}