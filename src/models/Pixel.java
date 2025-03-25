package models;

public class Pixel {
    private Integer redPixel;
    private Integer greenPixel;
    private Integer bluePixel;
    public Pixel(){
        this.redPixel = 0;
        this.greenPixel = 0;
        this.bluePixel = 0;
    }
    public Pixel(Integer redPixel, Integer greenPixel, Integer bluePixel){
        this.redPixel = redPixel;
        this.greenPixel = greenPixel;
        this.bluePixel = bluePixel;
    }

    public void setRedPixel(Integer redPixel) {
        this.redPixel = redPixel;
    }

    public void setGreenPixel(Integer greenPixel) {
        this.greenPixel = greenPixel;
    }

    public void setBluePixel(Integer bluePixel) {
        this.bluePixel = bluePixel;
    }

    public Integer getRedPixel() {
        return redPixel;
    }

    public Integer getGreenPixel() {
        return greenPixel;
    }

    public Integer getBluePixel() {
        return bluePixel;
    }
}

