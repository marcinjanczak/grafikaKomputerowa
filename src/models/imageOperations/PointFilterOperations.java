package models.imageOperations;

import models.ContrAndBrightModel;
import models.Pixel;

public class PointFilterOperations {
    public static Pixel[][] createNormalizeBrightnesImageArray(Pixel[][] pixelArray){
        int width = pixelArray.length;
        int height = pixelArray[0].length;

        ImageStats.MinMaxValues minMax = ImageStats.finMinMax(pixelArray);

        System.out.println("Min R: "+ minMax.minR);
        System.out.println("Max R: "+ minMax.maxR);

        System.out.println("Min G: "+ minMax.minG);
        System.out.println("Max G: "+ minMax.maxG);

        System.out.println("Min B: "+ minMax.minB);
        System.out.println("Max B: "+ minMax.maxB);

        int globalMin = Math.min(Math.min(minMax.minR, minMax.minG), minMax.maxB);
        int globalMax = Math.max(Math.max(minMax.maxR, minMax.maxG), minMax.maxB);

        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Pixel pixel = pixelArray[x][y];

                int r = 255 * (pixel.getRedPixel() - globalMin) / (globalMax - globalMin);
                int g = 255 * (pixel.getGreenPixel() - globalMin) / (globalMax - globalMin);
                int b = 255 * (pixel.getBluePixel() - globalMin) / (globalMax - globalMin);

                r = Math.max(0, Math.min(r, 255));
                g = Math.max(0, Math.min(g, 255));
                b = Math.max(0, Math.min(b, 255));

                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return newPixelArray;
    }

    public static Pixel[][] createNegativePixelImageArray(Pixel[][] pixelArray){
        int width = pixelArray.length;
        int height = pixelArray[0].length;

        ImageStats.MinMaxValues minMax = ImageStats.finMinMax(pixelArray);

        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Pixel pixel = pixelArray[x][y];

                int r = minMax.maxR - pixel.getRedPixel();
                int g = minMax.maxG - pixel.getRedPixel();
                int b = minMax.maxB - pixel.getBluePixel();

                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return newPixelArray;
    }

/// Zawiera algorytm który oblicza nowe wartości dla skłądowych pikseli, pobierając tablice oraz

    public static Pixel[][] createContrAndBrightPixelImageArray(Pixel[][] pixelArray, ContrAndBrightModel contrAndBrightModel){
        int width = pixelArray.length;
        int height = pixelArray[0].length;

        int kBrightness = contrAndBrightModel.getBrightness();
        double kContrast = contrAndBrightModel.getContrast();

        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Pixel pixel = pixelArray[x][y];

                int r = pixel.getRedPixel();
                int g = pixel.getGreenPixel();
                int b = pixel.getBluePixel();

                r = (int) (kContrast *(r -128) + 128);
                g = (int) (kContrast *(g -128) + 128);
                b = (int) (kContrast *(b -128) + 128);

                r += kBrightness;
                g += kBrightness;
                b += kBrightness;

                r = Math.max(0,Math.min(r,255));
                g = Math.max(0,Math.min(g,255));
                b = Math.max(0,Math.min(b,255));

                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return newPixelArray;
    }
}
