package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class is the base class for all ray
 * tracers in the renderer package. It contains the scene
 * to trace rays in.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructor for the RayTracerBase class
     *
     * @param scene the scene to trace rays in
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray in the scene and returns the color of the intersection point
     *
     * @param ray the ray to trace
     * @return the color of the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
