package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BezierDrawer {
    public void drawBezier(Graphics g, List<Point> controlPoints, Integer steps){
        if(controlPoints.size() < 2 ) return;
        System.out.println(steps);

        Point previous = null;
        for (int i = 0; i <= steps; i++){
            double t = i/(double)steps;
            Point current  = calculateBezierPoint(controlPoints, t);
            System.out.println(current.x+" "+ current.y);

            if(previous != null){
                g.drawLine(previous.x, previous.y, current.x, current.y);
            }
            previous = current;
            System.out.println(previous.x + " "+ previous.y);
        }
    }

    private Point calculateBezierPoint(List<Point> pointList, double t) {
        if(pointList.size() < 2){
            return new Point(0,0);
        }

        Point[] tmp = new Point[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            tmp[i] = new Point(pointList.get(i));
        }


        for (int k = pointList.size() - 1; k > 0; k--) {
            for (int i = 0; i < k; i++) {
                tmp[i] = new Point(
                        (int) ((1 - t) * tmp[i].x + t * tmp[i + 1].x),
                        (int) ((1 - t) * tmp[i].y + t * tmp[i + 1].y));
            }
        }
        return tmp[0];
    }
}
