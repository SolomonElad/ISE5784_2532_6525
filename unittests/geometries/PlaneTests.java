package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTests {

    /**
     * test method for {@link geometries.Plane#Plane(Point,Point,Point)}
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: check successful creation of plane
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)),
                "ERROR: Plane.Plane - construction failed");

        // =============== Boundary Values Tests ==================
        // TC10: two points collide
        assertThrows(IllegalArgumentException.class, //
                () ->new Plane(new Point(1, 0, 0),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)),
                "ERROR: Plane.Plane - no exception thrown for collision of 2 of the given points");

        // TC11: all 3 points are on the same line
        assertThrows(IllegalArgumentException.class, //
                () ->new Plane(new Point(1, 0, 0),
                        new Point(2, 0, 0),
                        new Point(3, 0, 0)),
                "ERROR: Plane.Plane - no exception thrown for 3 given points on the same line");

    }

    /**
     * test method for {@link geometries.Plane#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(-2, 0, 0);
        Point p2 = new Point(4, 0, 0);
        Point p3 = new Point(0, 6, 0);
        Vector norm = new Plane(p1, p2, p3).getNormal(p1);

        // TC01: check if normal is orthogonal to the plane (covers both directions in which the normal can be)
        assertEquals(0d,norm.dotProduct(p1.subtract(p2)),
                "ERROR: Plane.getNormal - normal is not orthogonal to plane");
        assertEquals(0d,norm.dotProduct(p1.subtract(p3)),
                "ERROR: Plane.getNormal - normal is not orthogonal to plane");

        // TC02: check if the normal is normalized
        assertEquals(1,
                norm.length(),
                0.000001,
                "ERROR: Plane.getNormal - does not return a normalized vector");
    }
}