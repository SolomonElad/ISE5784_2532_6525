package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Testing Polygons
 * @author Dan
 */
public class PolygonTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 1, 1)),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertex on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertices on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertices on a side");

    }

    /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void testFindIntersections() {
        Polygon polygon = new Polygon(new Point(-1,-2,4), new Point(-1,5,-3), new Point(4,2,-5), new Point(4,-2,-1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The intersection point is inside the polygon - simple (1 point)
        assertEquals(List.of(new Point(3.5,-1.55,-0.95)), polygon.findIntersections(new Ray(new Point(1,2,3), new Vector(2.5,-3.55,-3.95))),
                "ERROR: The intersection point supposed to be inside the polygon - not working as expected");

        // TC02: No intersection point is outside the polygon, against edge (0 point)
        assertNull(polygon.findIntersections(new Ray(new Point(1,2,3), new Vector(6,-12,-3))),
                "ERROR: No intersection point supposed to be against the polygon edge - not working as expected");

        // TC03: No intersection point is outside the polygon, against vertex (0 point)
        assertNull(polygon.findIntersections(new Ray(new Point(3,2,8), new Vector(-10,-9,-6))),
                "ERROR: No intersection point supposed to be against the polygon's vertex - not working as expected");

        // =============== Boundary Values Tests ==================
        // TC11: The point is on edge (0 point)
        assertNull(polygon.findIntersections(new Ray(new Point(3,2,8), new Vector(1,-2,-11))),
                "ERROR: No intersection point supposed to be on edge - not working as expected");

        // TC12: The point is in vertex (0 point)
        assertNull(polygon.findIntersections(new Ray(new Point(3,2,8), new Vector(1,0,-13))),
                "ERROR: No intersection point supposed to be in vertex - not working as expected");

        // TC13: The point is on edge's continuation (0 point)
        assertNull(polygon.findIntersections(new Ray(new Point(3,2,8), new Vector(-8,-4,0))),
                "ERROR: No intersection point supposed to be on edge's continuation - not working as expected");
    }
}
