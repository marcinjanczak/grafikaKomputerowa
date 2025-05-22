package models;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BezierDrawer {
    public void drawBezier(Graphics g, List<Point> controlPoints, Integer steps, int offsetX, int offsetY) {
        if (controlPoints.size() < 2) return;

        Point2D.Double previous = null;
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            Point2D.Double current = calculateBezierPoint(controlPoints, t);

            if (previous != null) {
                int x1 = (int) Math.round(previous.x) + offsetX;
                int y1 = (int) Math.round(previous.y) + offsetY;
                int x2 = (int) Math.round(current.x) + offsetX;
                int y2 = (int) Math.round(current.y) + offsetY;

                g.drawLine(x1,y1,x2,y2);
            }
            previous = current;
        }
    }

    private Point2D.Double calculateBezierPoint(List<Point> pointList, double t) {
        if (pointList.size() < 2) {
            return new Point2D.Double(0, 0);
        }

        Point2D.Double[] tmp = new Point2D.Double[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            tmp[i] = new Point2D.Double(pointList.get(i).x,pointList.get(i).y);
        }
        for (int k = pointList.size() - 1; k > 0; k--) {
            for (int i = 0; i < k; i++) {
                double x = (1 - t) * tmp[i].x + t * tmp[i + 1].x;
                double y = (1 - t) * tmp[i].y + t * tmp[i + 1].y;
                tmp[i] = new Point2D.Double(x,y);
            }
        }
        return tmp[0];
    }
    public double[][] multiplyMatrices(double[][] A, double[][] B) {
        double[][] result = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
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
