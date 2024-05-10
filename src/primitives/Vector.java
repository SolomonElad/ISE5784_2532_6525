package primitives;

/**
 * Class Vector is the basic class representing a vector in a 3D system.
 * The class is based on Point class.
 * The class is based on Double3 class.
 */
public class Vector extends Point {
    /**
     * Constructor for a vector in the 3D space
     * gets 3 coordinates that represent the vector
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     * @param z the z coordinate of the vector
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals((Double3.ZERO))) {
            throw new IllegalArgumentException("Zero vector is not allowed");
        }
    }

    /**
     * Constructor for a vector in the 3D space
     * gets a Double3 object that represents the vector
     * @param xyz the Double3 object that represents the vector
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);

        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Zero vector is not allowed");
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Vector{" + this.xyz + '}';
    }

    /**
     * Add a vector to another vector
     * @param vector the vector to add to the current vector
     * @return a vector that represents the addition of the two vectors
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * Scale a vector by a scalar
     * @param scalar the scalar to scale the vector by
     * @return a vector that represents the scaled vector
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Calculate the dot product of two vectors
     * @param vector the vector to calculate the dot product with
     * @return the dot product of the two vectors
     * formulas: d1 * d1 + d2 * d2 + d3 * d3
     */
    public double dotProduct(Vector vector) {
        return (this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3);
    }

    /**
     * Calculate the cross product of two vectors
     * @param vector the vector to calculate the cross product with
     * @return the cross product of the two vectors
     * formulas: (d2 * d3 - d2 * d3, -(d1 * d3 - d1 * d3), d1 * d2 - d1 * d2)
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.xyz.d2 * vector.xyz.d3 - vector.xyz.d2 * this.xyz.d3,
                (-(this.xyz.d1 * vector.xyz.d3 - vector.xyz.d1 * this.xyz.d3)),
                this.xyz.d1 * vector.xyz.d2 - vector.xyz.d1 * this.xyz.d2
                );
    }

    /**
     * Calculate the squared length of a vector
     * @return the squared length of the vector
     * formulas: d1 * d1 + d2 * d2 + d3 * d3
     */
    public double lengthSquared() {
        return (this.xyz.d1 * this.xyz.d1 +
                this.xyz.d2 * this.xyz.d2 +
                this.xyz.d3 * this.xyz.d3);
    }

    /**
     * Calculate the length of a vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalize a vector
     * @return a vector that represents the normalized vector
     * formulas: (d1 / length, d2 / length, d3 / length)
     */
    public Vector normalize() {
        return new Vector((xyz.reduce(length())));
    }
}
