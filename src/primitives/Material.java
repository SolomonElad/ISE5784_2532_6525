package primitives;

/**
 * Class Material represents the material of a geometry
 * this class is a PDS class
 */
public class Material {

    /**
     * The diffuse coefficient of the material
     */
    public Double3 kD = Double3.ZERO;
    /**
     * The specular coefficient of the material
     */
    public Double3 kS = Double3.ZERO;

    /**
     * The shininess of the material
     */
    public int shininess = 0;

    /**
     * setters for the diffuse coefficient of the material
     *
     * @param kD the diffuse coefficient of the material (Double3)
     * @return the material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setters for the diffuse coefficient of the material
     *
     * @param kD the diffuse coefficient of the material (double)
     * @return the material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * setters for the specular coefficient of the material
     *
     * @param kS the specular coefficient of the material (Double3)
     * @return the material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setters for the specular coefficient of the material
     *
     * @param kS the specular coefficient of the material (double)
     * @return the material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setters for the shininess of the material
     *
     * @param shininess the shininess of the material
     * @return the material
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }
}
