package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

public class BlackBoard {

    public static List<Point> generateAperturePoints(Point p0, Vector vTo, Vector vUp, Vector vRight, double aperture, int multipleRaysNum) {
        Random random = new Random();
        List<Point> points = new ArrayList<Point>();
        for(double i = -aperture; i < aperture; i+= aperture/multipleRaysNum){
            if(isZero(i)) continue;
            double jitterOffset =  random.nextDouble(-0.1,0.1);
            for(double j = -aperture; j < aperture; j+= aperture/multipleRaysNum){
                if(isZero(j)) continue;
                var p = p0.add(vUp.scale(i).add(vRight.scale(j + jitterOffset)));
                if(p0.distance(p) <= aperture) points.add(p);
            }
        }
        return points;
    }
}
