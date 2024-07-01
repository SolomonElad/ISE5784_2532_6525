package renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import primitives.*;
import scene.Scene;
//import scene.Scene;

/**
 * Testing Camera Class
 * @author Dan
 */
class CameraTest {
   /** Camera builder for the tests */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
      .setRayTracer(new SimpleRayTracer(new Scene("Test")))
      .setImageWriter(new ImageWriter("Test", 1, 1))
      .setLocation(Point.ZERO)
      .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
      .setVpDistance(10);

   /**
    * Test method for
    * {@link renderer.Camera#constructRay(int, int, int, int)}.
    */
   @Test
   void testConstructRay() {
      final String badRay  = "Bad ray";

      // ============ Equivalence Partitions Tests ==============
      // EP01: 4X4 Inside (1,1)
      Camera camera1 = cameraBuilder.setVpSize(8, 8).build();
      assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
                   camera1.constructRay(4, 4, 1, 1), badRay);

      // =============== Boundary Values Tests ==================
      // BV01: 4X4 Corner (0,0)
      assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
                   camera1.constructRay(4, 4, 0, 0), badRay);

      // BV02: 4X4 Side (0,1)
      assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
                   camera1.constructRay(4, 4, 1, 0), badRay);

      // BV03: 3X3 Center (1,1)
      Camera camera2 = cameraBuilder.setVpSize(6, 6).build();
      assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
                   camera2.constructRay(3, 3, 1, 1), badRay);

      // BV04: 3X3 Center of Upper Side (0,1)
      assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
                   camera2.constructRay(3, 3, 1, 0), badRay);

      // BV05: 3X3 Center of Left Side (1,0)
      assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
                   camera2.constructRay(3, 3, 0, 1), badRay);

      // BV06: 3X3 Corner (0,0)
      assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
                   camera2.constructRay(3, 3, 0, 0), badRay);

   }

   /**
    * Test method for
    * {@link Camera.Builder#setFocusPoint(Point)}
    */
    @Test
   void testSetFocusPoint() {
       cameraBuilder.setVpSize(6, 6);
       // ============ Equivalence Partitions Tests ==============

        //EP01: focus point has a different z value than the camera
        Camera camera = cameraBuilder
                .setFocusPoint(new Point(3, 0, 4)).build();
        assertEquals(new Vector(0.6, 0, 0.8), camera.getvTo(), "Bad vTo");
        assertEquals(new Vector(-0.8, 0, 0.6), camera.getvUp(), "Bad vUp");
        assertEquals(new Vector(0, -1, 0), camera.getvRight(), "Bad vRight");

        // =============== Boundary Values Tests =================
        // BVA01: focus point has the same z value as the camera
        camera = cameraBuilder
                .setFocusPoint(new Point(3, 4, 0)).build();
        assertEquals(new Vector(0.6, 0.8, 0), camera.getvTo(), "Bad vTo");
        assertEquals(new Vector(0.8, -0.6, 0), camera.getvRight(), "Bad vRight");
        assertEquals(new Vector(0, 0, 1), camera.getvUp(), "Bad vUp");

        // BVA02: focus point is directly below the camera
        camera = cameraBuilder
                .setFocusPoint(new Point(0, 0, -3)).build();
        assertEquals(new Vector(0, 0, -1), camera.getvTo(), "Bad vTo");
        assertEquals(new Vector(0, -1, 0), camera.getvUp(), "Bad vUp");
        assertEquals(new Vector(-1, 0, 0), camera.getvRight(), "Bad vRight");

        //BVA03: focus point is the camera's location
        assertThrows(IllegalArgumentException.class,
                () -> cameraBuilder.setFocusPoint(Point.ZERO),
                "ERROR: setting focus point to the camera's location does not throw an exception");

    }

  /**
   * Test method for
   * {@link Camera.Builder#setRotation(double)}
   */
    @Test
    void testSetRotation() {
       cameraBuilder.setVpSize(6, 6);
       // ============ Equivalence Partitions Tests ==============
       // EP01: rotation of 0
       Camera camera = cameraBuilder.setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
               .setRotation(0).build();
       assertEquals(new Vector(0, 0, -1), camera.getvTo(), "Bad vTo");
       assertEquals(new Vector(0, -1, 0), camera.getvUp(), "Bad vUp");
       assertEquals(new Vector(-1, 0, 0), camera.getvRight(), "Bad vRight");

       // EP02: rotation of 90
       camera = cameraBuilder.setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
               .setRotation(90).build();
       assertEquals(new Vector(0, 0, -1), camera.getvTo(), "Bad vTo");
       assertEquals(new Vector(-1, 0, 0), camera.getvUp(), "Bad vUp");
       assertEquals(new Vector(0, 1, 0), camera.getvRight(), "Bad vRight");

       // EP03: rotation of 180
       camera = cameraBuilder.setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
               .setRotation(180).build();
       assertEquals(new Vector(0, 0, -1), camera.getvTo(), "Bad vTo");
       assertEquals(new Vector(0, 1, 0), camera.getvUp(), "Bad vUp");
       assertEquals(new Vector(1, 0, 0), camera.getvRight(), "Bad vRight");

       // EP04: rotation of 270
       camera = cameraBuilder.setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
               .setRotation(270).build();
       assertEquals(new Vector(0, 0, -1), camera.getvTo(), "Bad vTo");
       assertEquals(new Vector(1, 0, 0), camera.getvUp(), "Bad vUp");
       assertEquals(new Vector(0, -1, 0), camera.getvRight(), "Bad vRight");

       // EP05: rotation of 360
       camera = cameraBuilder.setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
               .setRotation(360).build();
       assertEquals(new Vector(0, 0, -1), camera.getvTo(), "Bad vTo");
       assertEquals(new Vector(0, -1, 0), camera.getvUp(), "Bad vUp");
       assertEquals(new Vector(-1, 0, 0), camera.getvRight(), "Bad vRight");
    }
}