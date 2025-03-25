package models;

public class ImageStats {
    public static class MinMaxValues {
        public int minR, maxR;
        public int minG, maxG;
        public int minB, maxB;
    }
    public static MinMaxValues finMinMax(Pixel[][] pixelArray){
        MinMaxValues result = new MinMaxValues();
        for (int y = 0; y < pixelArray[0].length; y++) {
            for (int x = 0; x < pixelArray.length; x++) {
                Pixel pixel = pixelArray[x][y];

                // Składowa R
                if (pixel.getRedPixel() < result.minR) result.minR = pixel.getRedPixel();
                if (pixel.getRedPixel() > result.maxR) result.maxR = pixel.getRedPixel();

                // Składowa G
                if (pixel.getGreenPixel() < result.minG) result.minG = pixel.getGreenPixel();
                if (pixel.getGreenPixel() > result.maxG) result.maxG = pixel.getGreenPixel();

                // Składowa B
                if (pixel.getBluePixel() < result.minB) result.minB = pixel.getBluePixel();
                if (pixel.getBluePixel() > result.maxB) result.maxB = pixel.getBluePixel();
            }
        }
        return result;
    }
}
