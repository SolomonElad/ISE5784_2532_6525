//package renderer;
//
//import lighting.AmbientLight;
//import lighting.DirectionalLight;
//import lighting.PointLight;
//import lighting.SpotLight;
//import org.junit.jupiter.api.Test;
//import scene.Scene;
//import geometries.*;
//import scene.SceneBuilderFromXML;
//import primitives.*;
//
//import static java.awt.Color.*;
//
//public class hostage {
//
//    /**
//     * Scene of the tests
//     */
//    private Scene scene = SceneBuilderFromXML.setFromFile("xml/2284.xml");
//    //private Scene scene = new Scene("test");
//
//    /**
//     * Camera builder of the tests
//     */
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
////                new Sphere(new Point(0, 45, 20), 5d).setEmission(new Color(RED))
////                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
////                new Sphere(new Point(0, 45, 20), 2d).setEmission(new Color(yellow))
////                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
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
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
////    @Test
////    void panda() {
////            Scene scene = new Scene("Panda Scene");
////
////            Camera.Builder camera = Camera.getBuilder();
////
////            // גור פנדב - דוגמה באמצעות כמה כדורים
////            Geometry pandaBody = new Sphere(new Point(0, 0, 0), 50)
////                    .setEmission(new Color(50, 50, 50))
////                    .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));
////
////            Geometry pandaHead = new Sphere(new Point(0, 75, 0), 30)
////                    .setEmission(new Color(50, 50, 50))
////                    .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));
////
////            Geometry pandaEarLeft = new Sphere(new Point(-20, 95, 0), 10)
////                    .setEmission(new Color(50, 50, 50))
////                    .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));
////
////            Geometry pandaEarRight = new Sphere(new Point(20, 95, 0), 10)
////                    .setEmission(new Color(50, 50, 50))
////                    .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));
////
////            // הראי
////            Geometry mirror = new Plane(new Point(0, 0, 200), new Vector(0, 0, -1))
////                    .setEmission(new Color(100, 100, 100))
////                    .setMaterial(new Material().setKr(1));
////
////            // רקע של החדר
////            Geometry wall1 = new Plane(new Point(0, 0, -300), new Vector(0, 0, 1))
////                    .setEmission(new Color(200, 200, 200))
////                    .setMaterial(new Material().setKd(0.5));
////
////            Geometry wall2 = new Plane(new Point(-200, 0, 0), new Vector(1, 0, 0))
////                    .setEmission(new Color(200, 200, 200))
////                    .setMaterial(new Material().setKd(0.5));
////
////            Geometry wall3 = new Plane(new Point(200, 0, 0), new Vector(-1, 0, 0))
////                    .setEmission(new Color(200, 200, 200))
////                    .setMaterial(new Material().setKd(0.5));
////
////            Geometry floor = new Plane(new Point(0, -50, 0), new Vector(0, 1, 0))
////                    .setEmission(new Color(150, 75, 0))
////                    .setMaterial(new Material().setKd(0.5));
////
////            Geometry ceiling = new Plane(new Point(0, 200, 0), new Vector(0, -1, 0))
////                    .setEmission(new Color(150, 150, 150))
////                    .setMaterial(new Material().setKd(0.5));
////
////            // כדורים נוספים (לקישוט)
////            Geometry sphere1 = new Sphere(new Point(-100, 0, 50), 25)
////                    .setEmission(new Color(100, 150, 200))
////                    .setMaterial(new Material().setKr(0.3).setKd(0.5).setKs(0.5).setKt(0.3));
////
////            Geometry sphere2 = new Sphere(new Point(100, 0, 50), 25)
////                    .setEmission(new Color(200, 100, 150))
////                    .setMaterial(new Material().setKr(0.3).setKd(0.5).setKs(0.5).setKt(0.3));
////
////            Geometry sphere3 = new Sphere(new Point(0, 100, -100), 25)
////                    .setEmission(new Color(150, 200, 100))
////                    .setMaterial(new Material().setKr(0.3).setKd(0.5).setKs(0.5).setKt(0.3));
////
////            // הוספת הגופים לסצינה
////            scene.geometries.add(pandaBody, pandaHead, pandaEarLeft, pandaEarRight, mirror, wall1, wall2, wall3, floor, ceiling, sphere1, sphere2, sphere3);
////
////            // הגדרת התאורה
////            scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(-100, 100, 500), new Vector(1, -1, -2))
////                    .setKl(0.0001).setKq(0.00001));
////            scene.lights.add(new PointLight(new Color(500, 300, 0), new Point(50, 50, 50))
////                    .setKl(0.0005).setKq(0.0005));
////
////            // רינדור התמונה
////            camera
////                    .setDirection(new Vector(-1,0,0),new Vector(0, 1, 0))
////                    .setLocation(new Point(0, 150, 100))
////                    .setVpDistance(100)
////                    .setFocusPoint(new Point(0, 0, 0))
////                    .setVpSize(200, 200)
////                    .setImageWriter(new ImageWriter("PandaScene", 1000, 1000))
////                    .setRayTracer(new SimpleRayTracer(scene))
////                    .build()
////                    .renderImage()
////                    .writeToImage();
////        }
////    }
//}
