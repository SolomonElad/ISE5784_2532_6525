package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Geometries class
 */
class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Triangle triangle = new Triangle(new Point(0, 1, 1), new Point(2, 1, 0), new Point(1, 1, 0));
        Sphere sphere = new Sphere(new Point(1, 1, 0), 1d);
        Polygon polygon = new Polygon(new Point(0, 2, 1), new Point(1, 2, 0), new Point(2, 2, 0), new Point(1, 2, 4));
        Geometries geometries = new Geometries(plane, triangle, sphere, polygon);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some geometry's (but not all) are intersected
        assertEquals(3, geometries.findIntersections(new Ray(new Point(0, 2, 0), new Vector(2, -2, 0.5))).size(),
                "ERROR: Some geometry's (but not all) are intersected - not working as expected");

        // =============== Boundary Values Tests =================
        // TC01: empty collection (0 points)
        Geometries geometries1 = new Geometries();
        assertNull(geometries1.findIntersections(new Ray(new Point(0, 2, 0), new Vector(2, -2, 0.5))),
                "ERROR: empty collection - not working as expected");

        // TC02: no geometry is intersected (0 points)
        geometries.add(new Sphere(new Point(1, 2, 3), 3d));
        assertNull(geometries.findIntersections(new Ray(new Point(6, 7, 8), new Vector(2, 0, -2))),
                "ERROR: no geometry is intersected - not working as expected");

        // TC03: one geometry is intersected (2 points)
        assertEquals(1, geometries.findIntersections(new Ray(new Point(6, 7, 8), new Vector(0, -1, -2))).size(),
                "ERROR: one geometry is intersected - not working as expected");

        // TC04: all geometries are intersected (4 points)
        assertEquals(7, geometries.findIntersections(new Ray(new Point(1, 4, 8), new Vector(0, -3, -7.8))).size(),
                "ERROR: all geometries are intersected - not working as expected");
    }
}