package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 */
class RayTests {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The point is before the head of the ray (t < 0)
        assertEquals(new Point(0, 0, 0), ray.getPoint(-1),
                "ERROR: point is before the head of the ray (t < 0) - not working as expected");

        // TC02: The point is after the head of the ray (t > 0)
        assertEquals(new Point(2, 0, 0), ray.getPoint(1),
                "ERROR: point is after the head of the ray (t > 0) - not working as expected");

        // =============== Boundary Values Tests =================
        // TC03: The point is on the head of the ray (t = 0)
        assertEquals(new Point(1, 0, 0), ray.getPoint(0),
                "ERROR: point is on the head of the ray (t = 0) - not working as expected");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {

        List<Point> pointList = new LinkedList<>();

        pointList.add(new Point(1, 1, 1));
        pointList.add(new Point(2, 2, 2));
        pointList.add(new Point(3, 3, 3));

        Vector vector = new Vector(0, -0.5, 0);

        // ============ Equivalence Partitions Tests ==============
        //TC01: The closest point is in the middle of the list
        Ray ray1 = new Ray(new Point(2, 2.5, 2), vector);
        assertEquals(new Point(2, 2, 2), ray1.findClosestPoint(pointList), "The point in the middle");

        // =============== Boundary Values Tests ==================
        //TC10: The closest point is the first point in the list
        Ray ray2 = new Ray(new Point(1, 1.25, 1), vector);
        assertEquals(new Point(1, 1, 1), ray2.findClosestPoint(pointList), "The point is the first one");

        //TC11: The closest point is the last point in the list
        Ray ray3 = new Ray(new Point(3, 3.5, 3), vector);
        assertEquals(new Point(3, 3, 3), ray3.findClosestPoint(pointList), "The point is the last one");

        //TC12: The list is empty
        pointList.clear();
        assertNull(ray3.findClosestPoint(pointList), "The list is empty");
    }
}