package models.imageOperations;

import models.Pixel;

public class DilataionOperations {
    public static Pixel[][] createGrayByRed(Pixel[][] pixelArray){
        int width = pixelArray.length;
        int height = pixelArray[0].length;
        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Pixel pixel = pixelArray[x][y];

                int r = pixel.getRedPixel();
                int g = pixel.getRedPixel();
                int b = pixel.getRedPixel();

                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return newPixelArray;
    }
    public static Pixel[][] createGrayByGreen(Pixel[][] pixelArray){
        int width = pixelArray.length;
        int height = pixelArray[0].length;
        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Pixel pixel = pixelArray[x][y];

                int r = pixel.getGreenPixel();
                int g = pixel.getGreenPixel();
                int b = pixel.getGreenPixel();


                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return newPixelArray;
    }
    public static Pixel[][] createGrayByBlue(Pixel[][] pixelArray){
        int width = pixelArray.length;
        int height = pixelArray[0].length;
        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Pixel pixel = pixelArray[x][y];

                int r = pixel.getBluePixel();
                int g = pixel.getBluePixel();
                int b = pixel.getBluePixel();


                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return newPixelArray;
    }


    public static Pixel[][] createGrayByYUV(Pixel[][] pixelArray){
        int width = pixelArray.length;
        int height = pixelArray[0].length;
        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = pixelArray[x][y];

                int r = pixel.getRedPixel();
                int g = pixel.getGreenPixel();
                int b = pixel.getBluePixel();

                double doubleGray =(r * 0.299) + (g * 0.587) + (b * 0.114);

                int gray = (int) Math.round(doubleGray);

                r = gray;
                g = gray;
                b = gray;

                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return newPixelArray;
    }
    public static Pixel[][] createGrayPixelImageArray(Pixel[][] pixelArray){
    int width = pixelArray.length;
    int height = pixelArray[0].length;
    var newPixelArray = new Pixel[width][height];

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

            Pixel pixel = pixelArray[x][y];

            int r = pixel.getRedPixel();
            int g = pixel.getGreenPixel();
            int b = pixel.getBluePixel();

            int gray = (r + b + g) / 3;
            r = gray;
            g = gray;
            b = gray;

            newPixelArray[x][y] = new Pixel(r,g,b);
            }
        } return newPixelArray;
    }
}
