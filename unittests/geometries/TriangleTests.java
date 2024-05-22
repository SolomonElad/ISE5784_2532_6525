package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTests {

        /**
         * test method for {@link geometries.Triangle#getNormal(Point)}
         */
        @Test
        void testGetNormal() {
                // ============ Equivalence Partitions Tests ==============
                Point p1 =new Point(-2, 0, 0);
                Point p2 = new Point(4, 0, 0);
                Point p3 = new Point(0, 6, 0);
                Triangle t = new Triangle(p1, p2, p3);
                Vector norm = t.getNormal(p1);
                // TC01: check if normal is orthogonal to the triangle (covers both possible normal directions)
                assertEquals(0d,norm.dotProduct(p1.subtract(p2)),
                        "ERROR: Plane.getNormal - normal is not orthogonal to plane");
                assertEquals(0d,norm.dotProduct(p3.subtract(p2)),
                        "ERROR: Plane.getNormal -  normal is not orthogonal to plane");

                // TC02: check if the normal is normalized
                assertEquals(1,
                        norm.length(),
                        0.000001,
                        "ERROR: Triangle.getNormal - does not return a normalized vector");
        }

}