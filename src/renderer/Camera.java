package renderer;

import geometries.Plane;
import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.stream.IntStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * camera class represents the functionality of a camera in
 * our graphic scene, collecting the data needed for the rendering of the final picture
 */
public class Camera implements Cloneable {

    private Point p0;
    private Vector vTo, vUp, vRight;
    private double width = 0.0, height = 0.0, distance = 0.0;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private Point pCenter;

    //depth of field parameters
    private double aperture = 0.0;
    private double focalLength = 0.0;
    private boolean isModuleActive = false;
    private int multipleRaysNum = 0;
    private List<Point> aperturePoints;
    private Plane focalPlane;


    // private constructor - camera is built using a builder
    private Camera() {
    }

    /**
     * getter for camera's po Point
     *
     * @return camera's po Point
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter for camera's vTo Vector
     *
     * @return camera's vTo Vector
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * getter for camera's vUp Vector
     *
     * @return camera's vUp Vector
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * getter for camera's vRight Vector
     *
     * @return camera's vRight Vector
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * getter for camera's distance from view plane
     *
     * @return camera's distance from view plane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * getter for camera's height
     *
     * @return camera's height
     */
    public double getHeight() {
        return height;
    }

    /**
     * getter for camera's width
     *
     * @return camera's width
     */
    public double getWidth() {
        return width;
    }

    /**
     * builder function
     *
     * @return a new builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * method constructs a ray according to the center of a pixel
     *
     * @param nX - width resolution of view plane
     * @param nY - height resolution of view plane
     * @param j  - horizontal index of the pixel
     * @param i  - vertical index of the pixel
     * @return - a ray that passes in center of the pixel
     * NOTE: the order of indexing parameters is different from the standard [i,j]
     * for the sake of a persistent overall width-first order in class
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //place point in center of view plane
        Point PIJ = pCenter;
        double yI = -((i - (double) (nY - 1) / 2) * height / nY);
        double xJ = ((j - (double) (nX - 1) / 2) * width / nX);
        //move PIJ only of distances xJ & yI are not zero
        if (!isZero(yI))
            PIJ = PIJ.add(vUp.scale(yI));
        if (!isZero(xJ))
            PIJ = PIJ.add(vRight.scale(xJ));

        //return a ray from starting point to PIJ
        return new Ray(p0, PIJ.subtract(p0));
    }

    /**
     * method renders the image using the camera and ray tracer
     * and writes the image to a file
     *
     * @return the camera object
     */
//    public Camera renderImage() {
//        if (imageWriter == null)
//            throw new UnsupportedOperationException("Missing imageWriter");
//        if (rayTracer == null)
//            throw new UnsupportedOperationException("Missing rayTracerBase");
//
//        int Nx = imageWriter.getNx();
//        int Ny = imageWriter.getNy();
//
//        for (int i = 0; i < Ny; i++) {
//            for (int j = 0; j < Nx; j++) {
//                castRay(Nx, Ny, j, i);
//            }
//        }
//        return this;
//    }

    //faster version of renderImage - using parallel stream
    public Camera renderImage() {
        if (imageWriter == null)
            throw new UnsupportedOperationException("Missing imageWriter");
        if (rayTracer == null)
            throw new UnsupportedOperationException("Missing rayTracerBase");

        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        IntStream.range(0, Ny).parallel().forEach(i -> {
            IntStream.range(0, Nx).parallel().forEach(j -> {
                castRay(Nx, Ny, j, i);
            });
        });

        return this;
    }

//    public Camera renderImage() {
//        if (imageWriter == null)
//            throw new UnsupportedOperationException("Missing imageWriter");
//        if (rayTracer == null)
//            throw new UnsupportedOperationException("Missing rayTracerBase");
//
//        int Nx = imageWriter.getNx();
//        int Ny = imageWriter.getNy();
//
//        int numThreads = 2 * Runtime.getRuntime().availableProcessors();
//        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
//
//        for (int i = 0; i < Ny; i++) {
//            final int y = i;
//            executor.submit(() -> {
//                for (int j = 0; j < Nx; j++) {
//                    castRay(Nx, Ny, j, y);
//                }
//            });
//        }
//
//        executor.shutdown();
//        try {
//            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return this;
//    }

    /**
     * method casts a ray from the camera to a pixel in the view plane
     *
     * @param Nx     - width resolution of view plane
     * @param Ny     - height resolution of view plane
     * @param column - horizontal index of the pixel
     * @param row    - vertical index of the pixel
     */
    private void castRay(int Nx, int Ny, int column, int row) {
        if(!isModuleActive)
            imageWriter.writePixel(column, row, rayTracer.traceRay(constructRay(Nx, Ny, column, row)));
        else{
            Color color = rayTracer.traceRay(constructRay(Nx, Ny, column, row));
            aperturePoints = BlackBoard.generateAperturePoints(p0, vTo, vUp, vRight, aperture, multipleRaysNum);
            //calculate focal point
            //on focal plane
            Point focalPoint = focalPlane.findIntersections(constructRay(Nx, Ny, column, row)).getFirst();
            //shoot the rays from the aperture points to the focal point
            for (Point point : aperturePoints) {
                color = color.add(rayTracer.traceRay(new Ray(point,focalPoint.subtract(point))));
            }
            //calculate the average color of the rays
            color = color.reduce(aperturePoints.size()+1);
            imageWriter.writePixel(column, row, color);
        }
    }

    /**
     * method prints a grid on the view plane
     *
     * @param interval interval between grid lines
     * @param color    color of the grid
     * @return the camera object
     */
    public Camera printGrid(int interval, Color color) {
        // save the original resolution - for save time in the loop
        double Nx = imageWriter.getNx();
        double Ny = imageWriter.getNy();

        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }

    /**
     * method writes the image to a file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new UnsupportedOperationException("Missing imageWriter");
        imageWriter.writeToImage();
    }


    //////////////////////////// Builder implementation ////////////////////////////
    /**
     * ____Inner Class____
     * class builder is used for building a camera object. it is used due to the large amount of parameters
     * that a camera objects needs, to ensure simpler and understandable construction
     */
    public static class Builder {
        private final Camera camera;

        /**
         * builder constructor - initializes a new empty camera
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * builder constructor - initializes a new camera with a given camera object
         *
         * @param camera - camera object to be used as a base
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * builder function - set camera location
         *
         * @param p0 - point for camera's location
         * @return builder object with the updated camera
         */
        public Builder setLocation(Point p0) {
            camera.p0 = p0;
            return this;
        }

        /**
         * builder function - set camera direction vectors
         *
         * @param vTo - forward vector
         * @param vUp - upward vector
         * @return builder object with the updated camera
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("vectors vTo and Vup are not orthogonal");

            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * builder function - set camera's view plane size
         *
         * @param width  - view plane's width
         * @param height - view plane's height
         * @return builder object with the updated camera
         */
        public Builder setVpSize(double width, double height) {
            if (isZero(width) || width < 0)
                throw new IllegalArgumentException("view plane width must be a positive number");
            if (isZero(height) || height < 0)
                throw new IllegalArgumentException("view plane height must be a positive number");

            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * builder function - set camera's distance from view plane
         *
         * @param distance - camera's distance from view plane
         * @return builder object with the updated camera
         */
        public Builder setVpDistance(double distance) {
            if (isZero(distance) || distance < 0)
                throw new IllegalArgumentException("view plane width needs to be located after the camera");

            camera.distance = distance;
            return this;
        }

        /**
         * builder function - set camera's point of direction
         *
         * @param pTo - point of direction
         * @return builder object with the updated camera
         */
        public Builder setFocusPoint(Point pTo) {
            //vTo will be towards pTo
            if (pTo == camera.p0)
                throw new IllegalArgumentException("camera cannot be directed at itself");
            camera.vTo = pTo.subtract(camera.p0).normalize();

            //calculate vUp and vRight - if vTo is parallel to xy plane, vUp will simply face up
            if (isZero(pTo.getZ() - camera.getP0().getZ())) {
                camera.vUp = new Vector(0, 0, 1);
            } else if (camera.vTo.equals(new Vector(0, 0, 1))) {
                camera.vUp = new Vector(0, 1, 0);
            } else if (camera.vTo.equals(new Vector(0, 0, -1))) {
                camera.vUp = new Vector(0, -1, 0);
            }
            //else, using the plane vto is on that is orthogonal to xy plane, calculate vRight
            // then produce vUp
            else {
                camera.vRight = new Plane(camera.p0, pTo, new Point(pTo.getX(), pTo.getY(), camera.p0.getZ()))
                        .getNormal().normalize();
                camera.vUp = camera.vTo.crossProduct(camera.vRight).normalize();
            }
            if(camera.isModuleActive)
                camera.focalLength = camera.p0.distance(pTo);
            return this;
        }

        /**
         * builder function - set camera's rotation angle
         *
         * @param theta - degree of rotation
         * @return builder object with the updated camera
         */
        public Builder setRotation(double theta) {
            //rotate vUp and vRight vectors around vTo vector using rotation matrix
            double cosTheta = Math.cos(Math.toRadians(theta));
            double sinTheta = Math.sin(Math.toRadians(theta));
            Vector vUp = camera.vUp;
            double[][] rotationMatrix = getRotationMatrix(cosTheta, sinTheta);
            //multiply rotation matrix by vUp and vRight vectors
            camera.vUp = new Vector(
                    alignZero(rotationMatrix[0][0] * vUp.getX() + rotationMatrix[0][1] * vUp.getY() + rotationMatrix[0][2] * vUp.getZ()),
                    alignZero(rotationMatrix[1][0] * vUp.getX() + rotationMatrix[1][1] * vUp.getY() + rotationMatrix[1][2] * vUp.getZ()),
                    alignZero(rotationMatrix[2][0] * vUp.getX() + rotationMatrix[2][1] * vUp.getY() + rotationMatrix[2][2] * vUp.getZ())
            ).normalize();
            //vRight is orthogonal to vTo and vUp - will be calculated at the final build

            return this;
        }

        private double[][] getRotationMatrix(double cosTheta, double sinTheta) {
            Vector vTo = camera.vTo;
            //build rotation matrix - rotation clockwise around vTo vector
            return new double[][]{
                    {cosTheta + vTo.getX() * vTo.getX() * (1 - cosTheta),
                            vTo.getX() * vTo.getY() * (1 - cosTheta) - vTo.getZ() * sinTheta,
                            vTo.getX() * vTo.getZ() * (1 - cosTheta) + vTo.getY() * sinTheta},
                    {vTo.getY() * vTo.getX() * (1 - cosTheta) + vTo.getZ() * sinTheta,
                            cosTheta + vTo.getY() * vTo.getY() * (1 - cosTheta),
                            vTo.getY() * vTo.getZ() * (1 - cosTheta) - vTo.getX() * sinTheta},
                    {vTo.getZ() * vTo.getX() * (1 - cosTheta) - vTo.getY() * sinTheta,
                            vTo.getZ() * vTo.getY() * (1 - cosTheta) + vTo.getX() * sinTheta,
                            cosTheta + vTo.getZ() * vTo.getZ() * (1 - cosTheta)}
            };

        }

        /**
         * builder function - set camera's image writer
         *
         * @param imageWriter - image writer object
         * @return builder object with the updated camera
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null)
                throw new IllegalArgumentException("image writer cannot be null");
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * builder function - set camera's ray tracer
         *
         * @param rayTracer - ray tracer object
         * @return builder object with the updated camera
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            if (rayTracer == null)
                throw new IllegalArgumentException("ray tracer cannot be null");
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * builder function - set camera's aperture size
         *
         * @param aperture - aperture size
         * @return builder object with the updated camera
         */
        public Builder setAperture(double aperture) {
            if (isZero(aperture) || aperture <= 0)
                throw new IllegalArgumentException("aperture size must be a positive number");

            camera.aperture = aperture;
            camera.isModuleActive = true;
            return this;
        }

        /**
         * builder function - set camera's focal length
         *
         * @param focalLength - focal length
         * @return builder object with the updated camera
         */
        public Builder setFocalLength(double focalLength) {
            if (isZero(focalLength) || focalLength <= 0)
                throw new IllegalArgumentException("focal length must be a positive number");

            camera.focalLength = focalLength;
            return this;
        }

        /*
         * builder function - set number of rays for depth of field
         *
         * @param num - number of rays
         * @return builder object with the updated camera
         */
        public Builder setMultipleRaysNum(int num) {
            if (num <= 0)
                throw new IllegalArgumentException("number of rays must be a positive number");
            camera.multipleRaysNum = num;
            return this;
        }

        /**
         * final builder function, checking all resources are given and filling in computed resources
         *
         * @return a clone of the fully built Camera object
         */
        public Camera build() {
            String missingResource = "Missing rendering resource";
            String cameraClass = "Camera class";
            //check for missing resources
            if (camera.p0 == null)
                throw new java.util.MissingResourceException(missingResource, cameraClass, "missing location point");
            if (camera.vTo == null)
                throw new java.util.MissingResourceException(missingResource, cameraClass, "missing forward direction vector");
            if (camera.vUp == null)
                throw new java.util.MissingResourceException(missingResource, cameraClass, "missing upward direction vector");
            if (camera.width == 0.0)
                throw new java.util.MissingResourceException(missingResource, cameraClass, "missing view plane width");
            if (camera.height == 0.0)
                throw new java.util.MissingResourceException(missingResource, cameraClass, "missing view plane height");
            if (camera.distance == 0.0)
                throw new java.util.MissingResourceException(missingResource, cameraClass,
                        "missing view plane distance from camera");
            if (camera.imageWriter == null)
                throw new java.util.MissingResourceException(missingResource, cameraClass, "missing image writer");
            if (camera.rayTracer == null)
                throw new java.util.MissingResourceException(missingResource, cameraClass, "missing ray tracer");

            //check for depth of field resources
            if (camera.isModuleActive) {
                if (camera.aperture == 0.0)
                    throw new java.util.MissingResourceException(missingResource, cameraClass, "missing aperture size");
                if (camera.focalLength == 0.0)
                    throw new java.util.MissingResourceException(missingResource, cameraClass, "missing focal length");
                if (camera.multipleRaysNum == 0)
                    throw new java.util.MissingResourceException(missingResource, cameraClass, "missing number of rays");
            }

            //adding computed resources
            //NOTE: algebra-wise, vRight does not need to be normalized because camera's vTo and vUp are
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.pCenter = camera.p0.add(camera.vTo.scale(camera.distance));
            camera.focalPlane = new Plane(camera.p0.add(camera.vTo.scale(camera.focalLength)), camera.vTo);
            //return clone of final camera object
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError("unexpected error - cloning camera failed");
            }
        }
    }
}