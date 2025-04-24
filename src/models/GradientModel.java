package models;

import javax.swing.*;

public class GradientModel {
    private String name;
    private int thershold;
    private String mode;
    public GradientModel(String name, int thershold,String mode){
        this.name = name;
        this.thershold = thershold;
        this.mode = mode;
    }
    public String getName() {
        return name;
    }
    public int getThershold() {
        return thershold;
    }

    public String getMode() {
        return mode;
    }
}
