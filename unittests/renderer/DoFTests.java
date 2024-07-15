package renderer;

import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

/**
 * The DoFTests class is used to test the Depth of Field effect in the renderer package.
 */

public class DoFTests {
    @Test
    /**
     * Test the Depth of Field effect in the panda scene.
     */
    void pandaScene() {
        Scene scene = new Scene("Panda Scene DoF");

        Camera.Builder camera = Camera.getBuilder();

        Geometry pandaBody = new Sphere(new Point(0, 0, 0), 50)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaHead = new Sphere(new Point(0, 75, 0), 30)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaEarLeft = new Sphere(new Point(-20, 95, 0), 10)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaEarRight = new Sphere(new Point(20, 95, 0), 10)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaEyeLeft = new Sphere(new Point(-10, 80, 25), 5)
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaEyeRight = new Sphere(new Point(10, 80, 25), 5)
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaNose = new Sphere(new Point(0, 70, 30), 3)
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaLegFrontLeft = new Sphere(new Point(-20, -50, 0), 10)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaLegFrontRight = new Sphere(new Point(20, -50, 0), 10)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaLegBackLeft = new Sphere(new Point(-20, -50, 30), 10)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        Geometry pandaLegBackRight = new Sphere(new Point(20, -50, 30), 10)
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setKr(0.1).setKd(0.5).setKs(0.5));

        // Mirror
        Geometry mirror = new Plane(new Point(0, 0, 200), new Vector(0, 0, -1))
                .setEmission(new Color(100, 100, 100).scale(0.5))
                .setMaterial(new Material().setKr(1));

        // Walls and ceiling with colors
        Geometry wallBack = new Plane(new Point(0, 0, -300), new Vector(0, 0, 1))
                .setEmission(new Color(173, 216, 230).scale(0.5)) // Light Blue
                .setMaterial(new Material().setKd(0.5));

        Geometry wallLeft = new Plane(new Point(-200, 0, 0), new Vector(1, 0, 0))
                .setEmission(new Color(135, 206, 235).scale(0.5)) // Sky Blue
                .setMaterial(new Material().setKd(0.5));


        // Creating the house inside the picture frame
        Geometry houseBase = new Polygon(
                new Point(-200, 40, -190),   // Bottom left corner of the house base
                new Point(-200, 40, -110),   // Bottom right corner of the house base
                new Point(-200, 70, -110),   // Top right corner of the house base
                new Point(-200, 70, -190)    // Top left corner of the house base
        )
                .setEmission(new Color(139, 69, 19))  // Brown color for the house base
                .setMaterial(new Material().setKd(0.5));

        Geometry houseRoof = new Triangle(
                new Point(-200, 70, -190),   // Bottom left corner of the roof
                new Point(-200, 70, -110),   // Bottom right corner of the roof
                new Point(-200, 100, -150)   // Top center point of the roof
        )
                .setEmission(new Color(165, 42, 42))  // Dark red color for the roof
                .setMaterial(new Material().setKd(0.5));

        // Creating the sky above the house
        Geometry sky = new Polygon(
                new Point(-200, 100, -200),  // Top left corner of the sky
                new Point(-200, 100, -100),  // Top right corner of the sky
                new Point(-200, 70, -100),   // Bottom right corner of the sky
                new Point(-200, 70, -200)    // Bottom left corner of the sky
        )
                .setEmission(new Color(135, 206, 235))  // Sky blue color
                .setMaterial(new Material().setKd(0.5));

        // Creating the ground below the house
        Geometry ground = new Polygon(
                new Point(-200, 40, -200),   // Bottom left corner of the ground
                new Point(-200, 40, -100),   // Bottom right corner of the ground
                new Point(-200, 0, -100),    // Top right corner of the ground
                new Point(-200, 0, -200)     // Top left corner of the ground
        )
                .setEmission(new Color(34, 139, 34).scale(0.5))  // Green color for the ground
                .setMaterial(new Material().setKd(0.5));

        scene.geometries.add(houseBase, houseRoof, sky, ground);

        // Right wall as a mirror
        Geometry wallRight = new Plane(new Point(200, 0, 0), new Vector(-1, 0, 0))
                .setEmission(new Color(20, 20, 20).scale(0.5))
                .setMaterial(new Material().setKr(1).setKt(0).setKs(1));

        Geometry floor = new Plane(new Point(0, -50, 0), new Vector(0, 1, 0))
                .setEmission(new Color(200, 180, 150).scale(0.5))
                .setMaterial(new Material().setKd(0.5));

        Geometry ceiling = new Plane(new Point(0, 200, 0), new Vector(0, -1, 0))
                .setEmission(new Color(240, 248, 255).scale(0.3)) // Alice Blue
                .setMaterial(new Material().setKd(0.5));

        Geometry sphere1 = new Sphere(new Point(-100, 0, 50), 25)
                .setEmission(new Color(100, 150, 200).scale(0.3))
                .setMaterial(new Material().setKr(0.3).setKd(0.5).setKs(0.5).setKt(0.3));

        Geometry sphere2 = new Sphere(new Point(100, 0, 50), 20)
                .setEmission(new Color(200, 100, 150).scale(0.3))
                .setMaterial(new Material().setKr(0.3).setKd(0.5).setKs(0.5).setKt(0.3));

        Geometry sphere3 = new Sphere(new Point(0, 215, -100), 25)
                .setEmission(new Color(150, 200, 100).scale(0.3))
                .setMaterial(new Material().setKr(0.3).setKd(0.5).setKs(0.5).setKt(0.3));

        scene.geometries.add(pandaBody, pandaHead, pandaEarLeft, pandaEarRight, pandaEyeLeft, pandaEyeRight, pandaNose,
                pandaLegFrontLeft, pandaLegFrontRight, pandaLegBackLeft, pandaLegBackRight, mirror, wallBack, wallLeft, wallRight, floor, ceiling, sphere1, sphere2, sphere3);

        // Added ceiling light
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 200, 0))
                .setKl(0.0005).setKq(0.0005));

        scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point(0, 120, 140), new Vector(-1, -1, -1))
                .setKl(0.00005).setKq(0.0003).setNarrowBeam(1));

        camera
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setLocation(new Point(0, 120, 100))
                .setVpDistance(40)
                .setFocusPoint(new Point(0, 75, 0))
                .setVpSize(200, 200)
                .setAperture(3d)
                .setFocalLength(100d)
                .setMultipleRaysNum(10)
                .setImageWriter(new ImageWriter("PandaScene DoF", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();

        camera
                .setImageWriter(new ImageWriter("PandaScene_2_Rotation_10 DoF", 1000, 1000))
                .setLocation(new Point(-50, 40, -300))
                .setFocusPoint(new Point(0, 75, 0)).setRotation(200)
                .build()
                .renderImage()
                .writeToImage();

        camera
                .setImageWriter(new ImageWriter("PandaScene_3_panda_view DoF", 1000, 1000))
                .setLocation(new Point(0, 140, -30))
                .setFocusPoint(new Point(0, 140, 200)).setRotation(3)
                .build()
                .renderImage()
                .writeToImage();

        camera
                .setLocation(new Point(-175, 120, 100))
                .setVpDistance(50)
                .setImageWriter(new ImageWriter("PandaScene_FocusPoint(0, 120, 100)_Rotation(270) DoF", 1000, 1000))
                .setFocusPoint(new Point(0, 120, 100))
                .setRotation(270)
                .build()
                .renderImage()
                .writeToImage();
    }
}
