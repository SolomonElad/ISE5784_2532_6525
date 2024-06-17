package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * integration tests for camera ray intersections with geometries
 */
public class CameraIntegrationsTest {
    Camera.Builder builder = new Camera.Builder().setVpDistance(1)
            .setVpSize(3, 3).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0));
    Camera camera;

    /**
     * help function - count all  camera-rays intersections with a given geometry
     * @param geometry - given geometry
     * @return number of overall intersections
     */
    public int countIntersections(Intersectable geometry) {
        int count = 0;
        Ray ray;
        List<Point> intersections;
        for (int j = 0; j < 3; j++)
            for (int i = 0; i < 3; i++) {
                ray = camera.constructRay(3, 3, j, i);
                intersections = geometry.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        return count;
    }

    /**
     * test for camera intersections with sphere
     */
    @Test
    void testCameraSphereIntersection() {
        Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        Sphere sphere2dot5 = new Sphere(new Point(0, 0, -2.5), 2.5);
        Sphere sphere2 = new Sphere(new Point(0, 0, -2), 2);
        Sphere sphere4 = new Sphere(new Point(0, 0, -0.5), 4);
        Sphere sphere0dot5 = new Sphere(new Point(0, 0, 1), 0.5);

        //TC01: a sphere with a radius of 1 (2 intersection points expected)
        camera = builder.setLocation(new Point(0, 0, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1)).build();
        assertEquals(2, countIntersections(sphere1),
                "Error - integration test for camera sphere intersections - wrong number of intersections");

        //TC02: a sphere with a radius of 2.5 (18 intersection points expected)
        camera = builder.setLocation(new Point(0, 0, 0.5)).build();
        assertEquals(18, countIntersections(sphere2dot5),
                "Error - integration test for camera sphere intersections - wrong number of intersections");

        //TC03: a sphere with a radius of 2 (10 intersection points expected)
        assertEquals(10, countIntersections(sphere2),
                "Error - integration test for camera sphere intersections - wrong number of intersections");

        //TC04: a sphere with a radius of 4 (9 intersection points expected)
        assertEquals(9, countIntersections(sphere4),
                "Error - integration test for camera sphere intersections - wrong number of intersections");

        //TC05: a sphere with a radius of 4 behind camera (0 intersection points expected)
        assertEquals(0, countIntersections(sphere0dot5),
                "Error - integration test for camera sphere intersections - wrong number of intersections");
    }

    /**
     * test for camera intersections with plane
     */
    @Test
    void testCameraPlaneIntersection() {
        Plane plane1 = new Plane(new Point(0, 0, -1), new Point(0, 1, -1), new Point(1, 0, -1));
        Plane plane2 = new Plane(new Point(0, 0, -2.7), new Point(0, 1, -2.5), new Point(1, 0, -2));
        Plane plane3 = new Plane(new Point(-1.5, 1.5, -1), new Point(0, 1.5, -10), new Point(-1.5, -1.5, -1));

        //TC01: a plane of z=a format - parallel to view plane(9 intersection points expected)
        camera = builder.setLocation(new Point(0, 0, 1))
                 .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .build();
        assertEquals(9, countIntersections(plane1),
                "Error - integration test for camera plane intersections - wrong number of intersections");

        //TC02: a plane not parallel to view plane (9 intersection points expected)
        assertEquals(9, countIntersections(plane2),
                "Error - integration test for camera plane intersections - wrong number of intersections");

        //TC03: a plane intersecting 6 points from view plane (6 intersection points expected)
        assertEquals(6, countIntersections(plane3),
                "Error - integration test for camera plane intersections - wrong number of intersections");
    }

    /**
     * test for camera intersections with triangle
     */
    @Test
    void testCameraTriangleIntersection() {
        Triangle triangle1 = new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        Triangle triangle2 = new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));

        //TC01: a triangle with 1 intersection point (1 intersection points expected)
        camera = builder.setLocation(new Point(0,0,1))
                 .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1)).build();
        assertEquals(1,countIntersections(triangle1),
                "Error - integration test for camera triangle intersections - wrong number of intersections");

        //TC02: a triangle with 2 intersection points (2 intersection points expected)
        assertEquals(2,countIntersections(triangle2),
                "Error - integration test for camera triangle intersections - wrong number of intersections");
    }
}