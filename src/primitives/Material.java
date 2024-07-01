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
     * The transparency coefficient of the material
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient of the material
     */
    public Double3 kR = Double3.ZERO;

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

    /**
     * setters for the transparency coefficient of the material
     *
     * @param kT the transparency coefficient of the material (Double3)
     * @return the material
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setters for the transparency coefficient of the material
     *
     * @param kT the transparency coefficient of the material (double)
     * @return the material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setters for the reflection coefficient of the material
     *
     * @param kR the reflection coefficient of the material (Double3)
     * @return the material
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setters for the reflection coefficient of the material
     *
     * @param kR the reflection coefficient of the material (double)
     * @return the material
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}
