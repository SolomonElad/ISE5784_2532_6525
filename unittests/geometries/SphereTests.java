package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence partitions Tests ==============
        // TC01: check correction of normal calculation (direction & length)
        Sphere sphere = new Sphere(new Point(1, 1, 2), 2);
        Vector norm = sphere.getNormal(new Point(1,1,0));
        assertEquals(norm,new Vector(0,0,-1),"ERROR: sphere vector is incorrect");
    }
}