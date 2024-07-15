package primitives;


/** Class Point represents a point in the 3D space
 *  The class is based on the Double3 class
 * */
public class Point {
    /** The point in the 3D space */
    protected final Double3 xyz;
    /** The point at the origin */
    public static final Point ZERO = new Point(Double3.ZERO);

    /** Constructor for a point in the 3D space
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     * */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /** Constructor for a point in the 3D space
     * @param xyz the Double3 object that represents the point
     * */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point point)
                && this.xyz.equals(point.xyz);
    }

    @Override
    public String toString() {
        return "Point{" + xyz + '}';
    }

    /** Subtract a point from another point
     * @param point the point to subtract from the current point
     * @return a vector that represents the subtraction of the two points
     * */
    public Vector subtract(Point point) {
        // The check if the vector is invalid ie vector 0 is performed in the constructor
        return new Vector(this.xyz.subtract(point.xyz));
    }

    /** Add a vector to a point
     * @param vector the vector to add to the current point
     * @return a point that represents the addition of the vector to the point
     * */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /** Calculate the squared distance between two points
     * @param point the point to calculate the distance from
     * @return the squared distance between the two points
     * */
    public double distanceSquared(Point point) {
        return ((point.xyz.d1 - this.xyz.d1) * (point.xyz.d1 - this.xyz.d1) +
                (point.xyz.d2 - this.xyz.d2) * (point.xyz.d2 - this.xyz.d2) +
                (point.xyz.d3 - this.xyz.d3) * (point.xyz.d3 - this.xyz.d3));
    }

    /** Calculate the distance between two points
     * @param point the point to calculate the distance from
     * @return the distance between the two points
     * */
    public double distance(Point point) {
        return Math.sqrt(this.distanceSquared(point));
    }

    // Getters - for the coordinates of the point
    /** Get the x coordinate of the point
     * @return the x coordinate of the point
     * */
    public double getX() {
        return xyz.d1;
    }

    /** Get the y coordinate of the point
     * @return the y coordinate of the point
     * */
    public double getY() {
        return xyz.d2;
    }

    /** Get the z coordinate of the point
     * @return the z coordinate of the point
     * */
    public double getZ() {
        return xyz.d3;
    }
}