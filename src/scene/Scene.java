package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;
import lighting.LightSource;

import java.util.LinkedList;
import java.util.List;

/**
 * contains the scene's name, background color, ambient light, and geometries
 * that make up the scene
 */
public class Scene {
    /**
     * name of the scene
     */
    public final String name;
    /**
     * background color of the scene
     */
    public Color background = Color.BLACK;
    /**
     * ambient light of the scene
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * geometries that make up the scene
     */
    public Geometries geometries = new Geometries();

    /**
     * list of light sources in the scene
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * constructor - creates and names a scene
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * sets the background color of the scene
     *
     * @param background the color to set the background to
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * sets the ambient light of the scene
     *
     * @param ambientLight the ambient light to set
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * sets the light sources of the scene
     *
     * @param lights the light sources to set
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * sets the geometries of the scene
     *
     * @param geometries the geometries to set
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}