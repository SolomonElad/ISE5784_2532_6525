package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 */
class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence partitions Tests ==============
        Vector v1 = new Vector(0, 0, 1);
        Ray r = new Ray(new Point(0, 0, 1), v1);
        Cylinder cyl = new Cylinder(r, 5, 10);

        // TC01: check correction of normal on cylinder's side
        assertEquals(new Vector(0.6, 0.8, 0), cyl.getNormal(new Point(3, 4, 2)),
                "ERROR: Cylinder.getNormal - incorrect tube vector for side");

        //TC02: check correction of normal on cylinder's close-to-axis base
        assertEquals(v1.scale(-1), cyl.getNormal(new Point(2, 3, 1)),
                "ERROR: Cylinder.getNormal - incorrect tube vector for close-to-axis base");

        //TC03: check correction of normal on cylinder's far-from-axis base
        assertEquals(v1, cyl.getNormal(new Point(2, 2, 11)),
                "ERROR: Cylinder.getNormal - incorrect tube vector for far-from-axis base");

        // =============== Boundary Values Tests ==================
        // TC10: normal for the center of cylinder's close-to-axis base
        assertEquals(v1.scale(-1), cyl.getNormal(new Point(0, 0, 1)),
                "ERROR: Cylinder.getNormal - incorrect tube vector for far-from-axis base's center");

        // TC11: normal for the center of cylinder's far-from-axis base
        assertEquals(v1, cyl.getNormal(new Point(0, 0, 11)),
                "ERROR: Cylinder.getNormal - incorrect tube vector for far-from-axis base's center");

    }

    @Test
    void testFindIntersections() {
        // Not implemented
    }
}