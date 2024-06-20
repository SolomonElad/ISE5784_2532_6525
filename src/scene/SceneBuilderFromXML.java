package scene;

import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.*;
import geometries.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * builds a scene from an XML file
 */
public class SceneBuilderFromXML {

    // private helper function to get a Double3 from a string
    private static Double3 getDouble3(String point) {
        String[] xyz = point.split(" ");
        return new Double3(Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]));
    }

    /**
     * sets the scene from an XML file
     * @param filename the name of the XML file to set the scene from
     * @return the scene set from the XML file
     */
    public static Scene setFromFile(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filename);

            Element sceneElement = doc.getDocumentElement();

            Scene scene = new Scene(sceneElement.getAttribute("name"));

            // Set background color
            String[] rgb = sceneElement.getAttribute("background-color").split(" ");
            scene.setBackground(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));

            // Set ambient light
            Element ambientElement = (Element) sceneElement.getElementsByTagName("ambient-light").item(0);
            rgb = ambientElement.getAttribute("color").split(" ");
            if (ambientElement.hasAttribute("ka")) {
                scene.setAmbientLight(new AmbientLight(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])), Double.parseDouble(ambientElement.getAttribute("ka"))));
            } else {
                scene.setAmbientLight(new AmbientLight(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]))));
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
                            scene.geometries.add(new Sphere(center, radius));
                            break;
                        case "triangle":
                            Point p0 = new Point(getDouble3(geometryElement.getAttribute("p0")));
                            Point p1 = new Point(getDouble3(geometryElement.getAttribute("p1")));
                            Point p2 = new Point(getDouble3(geometryElement.getAttribute("p2")));
                            scene.geometries.add(new Triangle(p0, p1, p2));
                            break;
                        case "plane":
                            Point q0 = new Point(getDouble3(geometryElement.getAttribute("p0")));
                            if (geometryElement.hasAttribute("normal")) {
                                Vector normal = new Vector(getDouble3(geometryElement.getAttribute("normal")));
                                scene.geometries.add(new Plane(q0, normal));
                            } else {
                                Point P0 = new Point(getDouble3(geometryElement.getAttribute("p0")));
                                Point P1 = new Point(getDouble3(geometryElement.getAttribute("p1")));
                                Point P2 = new Point(getDouble3(geometryElement.getAttribute("p2")));
                                scene.geometries.add(new Plane(P0, P1, P2));
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
                            scene.geometries.add(new Polygon(vertices));
                            break;
                    }
                }
            }
            return scene;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
