package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTests {

    /**
     * Test method for {@link primitives.Vector#Vector(Double3)}.
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test - create a vector
        assertEquals(new Vector(1, 2, 3),
                new Vector(1, 2, 3),
                "ERROR: Vector(1, 2, 3) does not work correctly");

        // =============== Boundary Values Tests =================
        // TC11: create a vector with coordinates (0,0,0) - should throw exception
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "Vector(0,0,0) not thrown");
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple add test
        assertEquals(new Vector(1, 2, 3),
                new Vector(1, 1, 1).add(new Vector(0, 1, 2)),
                "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests =================
        // TC11: add vector to zero vector - should throw exception
        // cant happen because we have a constructor that checks if the vector is zero - this not valid

        // TC12: add vector to its opposite and equals in length - should throw exception
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)),
                "ERROR: Vector + (-Vector) does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple scale test
        assertEquals(new Vector(2, 4, 6),
                new Vector(1, 2, 3).scale(2),
                "ERROR: Vector * Scalar does not work correctly");

        // =============== Boundary Values Tests =================
        // TC11: scale by zero - should throw exception (zero vector)
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).scale(0),
                "ERROR: scaling by 0 does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple dot product test - any two vectors
        assertEquals(10,
                new Vector(1, 2, 3).dotProduct(new Vector(3, 2, 1)),
                "ERROR: dotProduct for orthogonal vectors does not work correctly");


        // =============== Boundary Values Tests =================
        // TC11: dot product with zero vector - cant happen
        // because we have a constructor that checks if the vector is zero - this not valid

        // TC12: Simple dot product test - orthogonal vectors - always 0
        assertEquals(0,
                new Vector(1, 0, 0).dotProduct(new Vector(0, 1, 0)),
                "ERROR: dotProduct for orthogonal vectors does not work correctly");

        // TC13: Simple dot product test - one vector length is 1
        assertEquals(3,
                new Vector(1, 2, 3).dotProduct(new Vector(0, 0, 1)),
                "ERROR: dotProduct for one vector length is 1 does not work correctly");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple cross product test - any two vectors
        assertEquals(new Vector(-4, 8, -4),
                new Vector(1, 2, 3).crossProduct(new Vector(3, 2, 1)),
                "ERROR: crossProduct for orthogonal vectors does not work correctly");

        // =============== Boundary Values Tests =================
        // TC11: cross product with zero vector
        // cant happen because we have a constructor that checks if the vector is zero - this not valid

        // TC12: Simple cross product test - parallel vectors - should throw exception
       assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).crossProduct(new Vector(-2, -4, -6)),
                "ERROR: crossProduct for opposite direction vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple length squared test
        assertEquals(14,
                new Vector(1, 2, 3).lengthSquared(),
                "ERROR: lengthSquared does not work correctly");

        // =============== Boundary Values Tests =================
        // TC11: length squared of zero vector - cant happen because we have a constructor that checks if the vector is zero - this not valid
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple length test
        assertEquals(5,
                new Vector(0, 3, 4).length(),
                "ERROR: length does not work correctly");

        // =============== Boundary Values Tests =================
        // TC11: length of zero vector - cant happen because we have a constructor that checks if the vector is zero - this not valid
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple normalize test
        assertEquals(new Vector(0 , 0.6, 0.8),
                new Vector(0, 3, 4).normalize(),
                "ERROR: normalize does not work correctly");

        // =============== Boundary Values Tests =================
        // TC11: normalize zero vector - cant happen because we have a constructor that checks if the vector is zero - this not valid
        // TC12: normalize vector with length 1 - already normalized
        assertEquals(new Vector(0, 1, 0),
                new Vector(0, 1, 0).normalize(),
                "ERROR: normalize does not work correctly");
    }
}