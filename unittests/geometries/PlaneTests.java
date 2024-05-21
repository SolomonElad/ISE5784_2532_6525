package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTests {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test - create a plane
        Vector vec = new Vector(1, 0, 0);
        Plane plane = new Plane(new Point(-2, 0, 0), new Point(4, 0, 0), new Point(0, 6, 0));
        assertTrue(plane.getNormal().equals(vec) || plane.getNormal().equals(vec.scale(-1)),
                "ERROR: Plane.getNormal() does not work correctly");

        // TC02: check if the normal is normalized
        assertEquals(1,
                plane.getNormal().length(),
                0.000001,
                "ERROR: Plane.getNormal() does not return a normalized vector");

        // ================= Boundary Values Tests =================
        // none
    }
}