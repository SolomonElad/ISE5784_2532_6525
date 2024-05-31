package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTests {

    /**
     * test method for {@link geometries.Triangle#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(-2, 0, 0);
        Point p2 = new Point(4, 0, 0);
        Point p3 = new Point(0, 6, 0);
        Triangle t = new Triangle(p1, p2, p3);
        Vector norm = t.getNormal(p1);
        // TC01: check if normal is orthogonal to the triangle (covers both possible normal directions)
        assertEquals(0d, norm.dotProduct(p1.subtract(p2)),
                "ERROR: Plane.getNormal - normal is not orthogonal to plane");
        assertEquals(0d, norm.dotProduct(p3.subtract(p2)),
                "ERROR: Plane.getNormal -  normal is not orthogonal to plane");

        // TC02: check if the normal is normalized
        assertEquals(1,
                norm.length(),
                0.000001,
                "ERROR: Triangle.getNormal - does not return a normalized vector");
    }

    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(-6, 6, 1), new Point(-7, 3, 5));

        // ============ Equivalence partitions Tests ==============
        // TC01: The intersection point is in the triangle (1 point)
        assertEquals(List.of(new Point(-4, 4, 1)),
                triangle.findIntersections(new Ray(new Point(1, 2, 3), new Vector(-5, 2, -2))),
                "ERROR: The point supposed to be in the triangle - not working as expected");

        // TC02: The intersection point is outside the triangle, against edge (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 2, 3), new Vector(-9, 3, 0))),
                "ERROR: The point supposed to be outside the triangle, against edge - not working as expected");

        // TC03: The intersection point is outside the triangle, against vertex (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 2, 3), new Vector(-11, 1.86, 4.14))),
                "ERROR: The point supposed to be outside the triangle, against vertex - not working as expected");

        // =============== Boundary Values Tests ==================
        // TC10: The point is on edge (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 2, 3), new Vector(-5, 0.14, -0.15))),
                "ERROR: The point supposed to be on edge - not working as expected");

        // TC11: The point is in vertex (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 2, 3), new Vector(-1, -1, -3))),
                "ERROR: The point supposed to be in vertex - not working as expected");

        // TC12: The point is on edge's continuation (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(3, 0, 0), new Vector(3, -4, -1))),
                "ERROR: The point supposed to be on edge's continuation - not working as expected");
    }
}