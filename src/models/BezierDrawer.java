package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BezierDrawer {
    public void drawBezier(Graphics g, List<Point> controlPoints, Integer steps, int offsetX, int offsetY) {
        if (controlPoints.size() < 2) return;
//        System.out.println(steps);

        Point previous = null;
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            Point current = calculateBezierPoint(controlPoints, t);
//            System.out.println(current.x+" "+ current.y);

            if (previous != null) {
                g.drawLine(previous.x + offsetX, previous.y + offsetY,
                        current.x + offsetX, current.y + offsetY);
            }
            previous = current;
//            System.out.println(previous.x + " "+ previous.y);
        }
    }

    private Point calculateBezierPoint(List<Point> pointList, double t) {
        if (pointList.size() < 2) {
            return new Point(0, 0);
        }

        Point[] tmp = new Point[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            tmp[i] = new Point(pointList.get(i));
        }
        for (int k = pointList.size() - 1; k > 0; k--) {
            for (int i = 0; i < k; i++) {
                double x = (1 - t) * tmp[i].x + t * tmp[i + 1].x;
                double y = (1 - t) * tmp[i].y + t * tmp[i + 1].y;
                tmp[i] = new Point((int) Math.round(x), (int) Math.round(y));
            }
        }
        return tmp[0];
    }
    public Double[][] calculateMatrix(){
        Double[][] matrix = new Double[3][3];

        return matrix;
    }
    public List<Point> calculateNewPoints(double[][] matrix, List<Point> pointList){
        List<Point> newPoints = new ArrayList<>();

        for(Point p :pointList){
            double x = matrix[0][0] * p.x + matrix[0][1] * p.y + matrix[0][2];
            double y = matrix[1][0] * p.x + matrix[1][1] * p.y + matrix[1][2];
            newPoints.add(new Point((int) Math.round(x), (int) Math.round(y)));
        }
        return  newPoints;
    }
    public double[][] createRotateMatrix(double rotateValue){
        double radians = Math.toRadians(rotateValue);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
//        System.out.println(cos);
//        System.out.println(sin);
        return new double[][]{
                {cos,    -sin,      0},
                {sin,    cos,      0},
                {0,      0,      1}
        };
    }
    public double[][] createMoveMatrix(double xValue, double yValue){
        return new double[][]{
                {1,      0,      xValue},
                {0,      1,      yValue},
                {0,      0,      1}
        };
    }
    public double[][] createScaleMatrix(double xValue, double yValue){
        return new double[][]{
                {xValue, 0,      0},
                {0,      yValue, 0},
                {0,      0,      1}
        };
    }
}
