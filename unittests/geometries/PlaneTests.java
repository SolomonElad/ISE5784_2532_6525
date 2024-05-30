package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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

    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane, starts outside the plane, neither parallel nor perpendicular to the plane (1 point)
        assertEquals(List.of(new Point(0.8,0.2,0)),
                plane.findIntersections(new Ray(new Point(2,5,6),new Vector(-1,-4,-5))),
                "Error: Ray intersects the plane - does not work as expected"
        );

        //TC02: Ray not intersects the plane, starts outside the plane, neither parallel nor perpendicular to the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(2,5,6),new Vector(1,1,1))),
                "Error: Ray does not intersects the plane - does not work as expected");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane (0 point)
        //TC10: The ray not included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(2,5,6), new Vector(1,1,-2))),
                "ERROR: Ray is parallel to the plane, and the ray not included in the plane");

        //TC11: The ray included in the plane (0 point ~infinite points)
        assertNull(plane.findIntersections(new Ray(new Point(2,0,-1), new Vector(-1,1,0))),
                "ERROR: Ray is parallel to the plane, and the ray included in the plane");

        // **** Group: Ray is orthogonal to the plane
        //TC12: according to the head point, before the plane (1 point)
        assertEquals(List.of(new Point(0,0,1)),
                plane.findIntersections(new Ray(new Point(-1,-1,0), new Vector(2,2,2))),
                "ERROR: Ray is orthogonal to the plane, according to the head point, before the plane");

        //TC13: according to the head point, in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1,1,1))),
                "ERROR: Ray is orthogonal to the plane, according to the head point, in the plane");

        //TC14: according to the head point, after the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(1,1,2), new Vector(1,1,1))),
                "ERROR: Ray is orthogonal to the plane, according to the head point, after the plane");

        // **** Group: Ray is neither orthogonal nor parallel to the plane
        //TC15: Ray begins at the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(2,-2,1), new Vector(-1,3,1))),
                "ERROR: Ray is neither orthogonal nor parallel to ray and begin at the plane");

        //TC16: Ray begins in the same point which appears as reference point in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(1,0,0), new Vector(0,1,2))),
                "ERROR: Ray is neither orthogonal nor parallel to ray " +
                        "and begins in the same point which appears as reference point in the plane");
    }
}