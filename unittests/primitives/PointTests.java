package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Point class
 */
class PointTests {

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple subtract test
        assertEquals(new Point(1, 2, 3),
                new Point(2, 4, 6).subtract(new Point(1, 2, 3)),
                "ERROR: (point2 - point1) does not work correctly");

        // ================= Boundary Values Tests =================
        // TC10: subtract point from itself - should throw exception
        assertThrows(IllegalArgumentException.class,
                () -> new Point(1, 2, 3).subtract(new Point(1, 2, 3)),
                "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple add test
        assertEquals(new Point(1, 2, 3),
                new Point(0, 1, 2).add(new Vector(1, 1, 1)),
                "ERROR: (point + vector) = other point does not work correctly");

        // ================= Boundary Values Tests =================
        // TC10: add opposite vector to point - should throw exception
        assertEquals(Point.ZERO,
                new Point(1, 2, 3).add(new Vector(-1, -2, -3)),
                "ERROR: (point + vector) = cannot add zero vector");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple distance squared test
        assertEquals(9,
                new Point(0, 0, 0).distanceSquared(new Point(0, 3, 0)),
                "ERROR: squared distance between points is wrong");

        // ================= Boundary Values Tests =================
        // TC10: distance squared between point and itself
        assertEquals(0,
                new Point(1, 2, 3).distanceSquared(new Point(1, 2, 3)),
                "ERROR: point squared distance to itself is not zero");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple distance test
        assertEquals(3,
                new Point(0, 0, 0).distance(new Point(0, 0, 3)),
                "ERROR: distance between points to itself is wrong");

        // ================= Boundary Values Tests =================
        // TC10: distance between point and itself
        assertEquals(0,
                new Point(1, 2, 3).distance(new Point(1, 2, 3)),
                "ERROR: point distance to itself is not zero");
    }
}