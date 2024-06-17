package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import geometries.*;
import primitives.*;


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
     * sets the geometries of the scene
     *
     * @param geometries the geometries to set
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    private Double3 getDouble3(String point) {
        String[] xyz = point.split(" ");
        return new Double3(Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]));
    }

    /**
     * sets the scene from an XML file
     *
     * @param filename the name of the XML file
     * @return the scene
     */
    public Scene setFromFile(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filename);

            Element sceneElement = doc.getDocumentElement();

            // Set background color
            String[] rgb = sceneElement.getAttribute("background-color").split(" ");
            setBackground(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));

            // Set ambient light
            Element ambientElement = (Element) sceneElement.getElementsByTagName("ambient-light").item(0);
            rgb = ambientElement.getAttribute("color").split(" ");
            if (ambientElement.hasAttribute("ka")) {
                setAmbientLight(new AmbientLight(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])), Double.parseDouble(ambientElement.getAttribute("ka"))));
            } else {
                setAmbientLight(new AmbientLight(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])), 1.0));
            }

            // Set geometries
            Element geometriesElement = (Element) sceneElement.getElementsByTagName("geometries").item(0);
            NodeList geometries = geometriesElement.getChildNodes();

            for (int i = 0; i < geometries.getLength(); i++) {
                if (geometries.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element geometryElement = (Element) geometries.item(i);
                    switch (geometryElement.getTagName()) {
                        case "sphere":
                            Point center = new Point(getDouble3(geometryElement.getAttribute("center")));
                            double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
                            this.geometries.add(new Sphere(center, radius));
                            break;
                        case "triangle":
                            Point p0 = new Point(getDouble3(geometryElement.getAttribute("p0")));
                            Point p1 = new Point(getDouble3(geometryElement.getAttribute("p1")));
                            Point p2 = new Point(getDouble3(geometryElement.getAttribute("p2")));
                            this.geometries.add(new Triangle(p0, p1, p2));
                            break;
                        case "plane":
                            Point q0 = new Point(getDouble3(geometryElement.getAttribute("p0")));
                            if (geometryElement.hasAttribute("normal")) {
                                Vector normal = new Vector(getDouble3(geometryElement.getAttribute("normal")));
                                this.geometries.add(new Plane(q0, normal));
                            } else {
                                Point P0 = new Point(getDouble3(geometryElement.getAttribute("p0")));
                                Point P1 = new Point(getDouble3(geometryElement.getAttribute("p1")));
                                Point P2 = new Point(getDouble3(geometryElement.getAttribute("p2")));
                                this.geometries.add(new Plane(P0, P1, P2));
                            }
                            break;
                        case "polygon":
                            int numPoints = geometryElement.getAttributes().getLength();
                            Point[] vertices = new Point[numPoints];
                            for (int j = 0; j < numPoints; j++) {
                                String pointAttribute = "p" + j;
                                if (geometryElement.hasAttribute(pointAttribute)) {
                                    vertices[j] = new Point(getDouble3(geometryElement.getAttribute(pointAttribute)));
                                }
                            }
                            this.geometries.add(new Polygon(vertices));
                            break;
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}