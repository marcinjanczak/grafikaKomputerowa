package models;

import java.awt.*;

public class LineModel {
    private final Integer intervals;
    private final Color color;

    public LineModel(){
        this.intervals = 20;
        this.color = Color.BLUE;
    }

    public LineModel(Integer intervals, Color color) {
        this.intervals = intervals;
        this.color = color;
    }

    public Integer getIntervals() {
        return intervals;
    }

    public Color getColor() {
        return color;
    }
}
