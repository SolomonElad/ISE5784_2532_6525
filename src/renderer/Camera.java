package renderer;

import geometries.Plane;
import primitives.*;

import static primitives.Util.isZero;

/**
 * camera class represents the functionality of a camera in
 * our graphic scene, collecting the data needed for the rendering of the final picture
 */
public class Camera implements Cloneable {

    private Point p0;
    private Vector vTo, vUp, vRight;
    private double width=0.0, height=0.0, distance=0.0;

    private Camera(){
    }


    /**
     * getters:
     */
    public double getDistance() {
        return distance;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    /**
     * method constructs a ray according to the center of a pixel
     * @param nX - width resolution of view plane
     * @param nY - height resolution of view plane
     * @param j - horizontal index of the pixel
     * @param i - vertical index of the pixel
     * @return - a ray that passes in center of the pixel
     * NOTE: the order of indexing parameters is different from the standard [i,j]
     * for the sake of a persistent overall width-first order in class
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        //place point in center of view plane
        Point PIJ = p0.add(vTo.scale(distance));
        double yI = -((i- (double) (nY - 1) /2)*height/nY);
        double xJ = ((j-(double)(nX-1)/2)*width/nX);
        //move PIJ only of distances xJ & yI are not zero
        if(!isZero(yI))
            PIJ = PIJ.add(vUp.scale(yI));
        if(!isZero(xJ))
            PIJ = PIJ.add(vRight.scale(xJ));

        //return a ray from p0 to PIJ
        return new Ray(p0, PIJ.subtract(p0));
    }


    /**
     * ____Inner Class____
     * class builder is used for building a camera object. it is used due to the large amount of parameters
     * that a camera objects needs, to ensure simpler and understandable construction
     */
    public static class Builder{
        private final Camera camera;

        /**
         * builder constructor - initializes a new empty camera
         */
        public Builder(){
            this.camera = new Camera();
        }


        /**
         * builder function - set camera location
         * @param p0 - point for camera's location
         * @return builder object with the updated camera
         */
        public Builder setLocation(Point p0)
        {
            camera.p0 = p0;
            return this;
        }


        /**
         * builder function - set camera direction vectors
         * @param vTo - forward vector
         * @param vUp - upward vector
         * @return builder object with the updated camera
         */
        public Builder setDirection(Vector vTo,Vector vUp)
        {
            if(!isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("vectors vTo and Vup are not orthogonal");

            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * builder function - set camera's view plane size
         * @param width - view plane's width
         * @param height - view plane's height
         * @return builder object with the updated camera
         */
        public Builder setVpSize(double width,double height)
        {
            if(isZero(width)||width<0)
                throw new IllegalArgumentException("view plane width must be a positive number");
            if(isZero(height)||height<0)
                throw new IllegalArgumentException("view plane height must be a positive number");

            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * builder function - set camera's distance from view plane
         * @param distance - camera's distance from view plane
         * @return builder object with the updated camera
         */
        public Builder setVpDistance(double distance)
        {
            if(isZero(distance)||distance<0)
                throw new IllegalArgumentException("view plane width needs to be located after the camera");

            camera.distance = distance;
            return this;
        }

        /**
         builder function - set camera's point of direction
         * @param pTo - point of direction
         * @return builder object with the updated camera
         * TODO:think logic at test later on
         */
        public Builder setFocusPoint(Point pTo) {
            //vTo will be towards pTo
            if (pTo == camera.p0)
                throw new IllegalArgumentException("camera cannot be directed at itself");
            camera.vTo = pTo.subtract(camera.p0).normalize();

            //calculate vUp and vRight - if vTo is parallel to xy plane, vUp will simply face up
            if (isZero(camera.vTo.getZ())) {
                camera.vUp = new Vector(0, 0, 1);
            }
            //else, using the plane vto is on that is orthogonal to xy plane, calculate vRight
            // then produce vUp
            else {
                camera.vRight = new Plane(camera.p0,pTo,new Point(pTo.getX(), pTo.getY(), camera.p0.getZ()))
                        .getNormal().normalize();
                camera.vUp = camera.vRight.crossProduct(camera.vUp).normalize();
            }

            return this;
        }


        /**
         builder function - set camera's rotation angle
         * @param theta - degree of rotation
         * @return builder object with the updated camera
         * TODO:think logic at test later on, add check for input
         */
        public Builder setRotation(double theta) {
            return this;
        }

            /**
             * final builder function, checking all resources are given and filling in computed resources
             * @return a clone of the fully built Camera object
             */
        public Camera build(){
            String missingResource = "Missing rendering resource";
            String cameraClass = "Camera class";
            //check for missing resources
            if(camera.p0==null)
                throw new java.util.MissingResourceException(missingResource,cameraClass,"missing location point");
            if(camera.vTo==null)
                throw new java.util.MissingResourceException(missingResource,cameraClass,"missing forward direction vector");
            if(camera.vUp==null)
                throw new java.util.MissingResourceException(missingResource,cameraClass,"missing upward direction vector");
            if(camera.width==0.0)
                throw new java.util.MissingResourceException(missingResource,cameraClass,"missing view plane width");
            if(camera.height==0.0)
                throw new java.util.MissingResourceException(missingResource,cameraClass,"missing view plane height");
            if(camera.distance==0.0)
                throw new java.util.MissingResourceException(missingResource,cameraClass,
                        "missing view plane distance from camera");

            //TODO: check for proper values - why check again?

            //adding computed resources
            //NOTE: algebra-wise, vRight does not need to be normalized because camera's vTo and vUp are
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            //return clone of final camera object
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError("unexpected error - cloning camera failed");
            }
        }
    }
}
