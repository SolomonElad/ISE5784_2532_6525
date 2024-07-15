//package renderer;
//
//import org.junit.jupiter.api.Test;
//import scene.SceneBuilderFromXML;
//import scene.Scene;
//import primitives.*;
//import geometries.*;
//import lighting.*;
//
//import static java.awt.Color.*;
//
//public class hostage {
//
//    private Scene scene = SceneBuilderFromXML.setFromFile("xml/2284.xml");
//    //private Scene scene = new Scene("test");
//
//    Camera.Builder camera = Camera.getBuilder();
//
//    @Test
//    void hostage() {
//        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));
//        scene.setBackground(new Color(212, 198, 158));
//
//        scene.lights.add(
//                new SpotLight(new Color(120, 120, 120), new Point(0, 150, 250), new Vector(-1, -1, -1))
//                        .setKl(4E-4).setKq(2E-5).setNarrowBeam(0.0000000000000000001)
//        );
//        scene.lights.add(new DirectionalLight(new Color(200, 200, 200), new Vector(-1.25, -1, -1)));
//
//
//        scene.geometries.add(
//                new Triangle(new Point(200, 200, -8), new Point(-600, 200, -8), new Point(200, -600, -8))
//                        .setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(212, 198, 158))
//                );
//
//        camera
//                .setLocation(new Point(0, -75, 100))
//                .setVpDistance(100)
//                .setVpSize(100, 100)
//                .setFocusPoint(new Point(0, 0, 0))
//                .setRotation(180)
//                .setRayTracer(new SimpleRayTracer(scene))
//                .setImageWriter(new ImageWriter("Hostages", 1000, 1000))
//                .setMultiThreading(3)
//                .setDebugPrint(0.1)
//                .build()
//                .renderImage()
//                .writeToImage();
//
//        camera
//                .setAperture(3d)
//                .setFocalLength(100d)
//                .setMultipleRaysNum(10)
//                .setImageWriter(new ImageWriter("Hostages DoF", 1000, 1000))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//}