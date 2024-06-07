package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The SimpleRayTracer class is a simple ray tracer that traces rays in a scene
 * and returns the color of the intersection point.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Constructor for the SimpleRayTracer class
     *
     * @param scene the scene to trace rays in
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections  = this.scene.geometries.findIntersections(ray);
        return intersections == null ? this.scene.background : calcColor(ray.findClosestPoint(intersections));
    }

    /**
     * Calculate the color of the intersection point
     *
     * @param point the intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}
