package models;

public class ContrAndBrightModel {
    private final Double contrast;
    private final int brightness;
    public ContrAndBrightModel(Double contrast, int brightness){
        this.contrast = contrast;
        this.brightness = brightness;
    }

    public Double getContrast() {
        return contrast;
    }

    public int getBrightness() {
        return brightness;
    }
}
