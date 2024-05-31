package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

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
        Vector norm = sphere.getNormal(new Point(1, 1, 0));
        assertEquals(norm, new Vector(0, 0, -1), "ERROR: sphere vector is incorrect");
    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Vector v100 = new Vector(1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)),
                "ERROR: Ray's line out of sphere - not working as expected");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "ERROR: Wrong number of points - not working as expected");
        assertEquals(exp, result1, "ERROR: Ray crosses sphere - not working as expected");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(1.25, 0.75, 0), new Vector(-1, 1, 0)));
        assertEquals(List.of(new Point(1, 1, 0)), result2,
                "ERROR: Ray crosses sphere on one point - not working as expected");
        assertEquals(1, result2.size(),
                "ERROR: Ray crosses sphere on one point - wrong number of points - not working as expected");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), v110)),
                "ERROR: Ray's line after the sphere - not working as expected");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result3 = sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 1, -2)));
        assertEquals(List.of(new Point(1, 0.8, -0.6)), result3,
                "ERROR: Ray starts at sphere and goes inside - not working as expected");
        assertEquals(1, result3.size(),
                "ERROR: Ray's line after the sphere - wrong number of points - not working as expected");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(-1, 0, 1))),
                "ERROR: Ray starts at sphere and goes outside - not working as expected");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result = sphere.findIntersections(new Ray(p01, v100))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result.size(), "ERROR: Wrong number of points - Ray starts before the sphere" +
                " - not working as expected");
        assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)),
                result, "ERROR: Ray starts before the sphere - not working as expected");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result4 = sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, -1)));
        assertEquals(List.of(new Point(1, 0, -1)), result4,
                "ERROR: Ray starts at sphere and goes inside - not working as expected");
        assertEquals(1, result4.size(),
                "ERROR: Ray starts at sphere and goes inside - wrong number of points - not working as expected");

        // TC15: Ray starts inside (1 point)
        final var result5 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), v100));
        assertEquals(List.of(new Point(2, 0, 0)), result5,
                "ERROR: Ray starts inside - not working as expected");
        assertEquals(1, result5.size(),
                "ERROR: Ray starts inside - wrong number of points - not working as expected");

        // TC16: Ray starts at the center (1 point)
        final var result6 = sphere.findIntersections(new Ray(p100, new Vector(1, 0, 0)));
        assertEquals(List.of(new Point(2, 0, 0)), result6,
                "ERROR: Ray starts at the center - not working as expected");
        assertEquals(1, result6.size(),
                "ERROR: Ray starts at the center - wrong number of points - not working as expected");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), v100)),
                "ERROR: Ray starts at sphere and goes outside - not working as expected");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), v100)),
                "ERROR: Ray starts after sphere - not working as expected");

        // **** Group: Ray's line is tangent to the sphere
        // TC19: Ray starts before the tangent point (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 1), v100)),
                "ERROR: Ray starts before the tangent point - not working as expected");

        // TC20: Ray starts at the tangent point (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), v100)),
                "ERROR: Ray starts at the tangent point - not working as expected");

        // TC21: Ray starts after the tangent point (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 1), v100)),
                "ERROR: Ray starts after the tangent point - not working as expected");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 2), v100)),
                "ERROR: Ray's line is outside, ray is orthogonal to ray start to sphere's center line " +
                        "- not working as expected");
    }
}