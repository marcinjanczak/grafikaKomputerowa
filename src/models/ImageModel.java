package models;

import models.imageOperations.DilataionOperations;
import models.imageOperations.PointFilterOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageModel {

    private BufferedImage image;

    public ImageModel(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    /// Utwórz kopie obrazu
    ///
    public BufferedImage getCopyImage() {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return copy;
    }
    /// Narysuj koło na obrazie
    ///
    public void drawCircle(CircleModel circle) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(circle.getColor());
            g2d.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
            g2d.dispose();
        }
    }
    /// Narysuj prostokąt na obrazie
    ///
    public void drawRectangle(RectangleModel rectangle) {
        if(image != null){
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image,0,1,null);
            g2d.setColor(rectangle.getColor());
            g2d.fillRect(
                    rectangle.getStartX(),
                    rectangle.getStartY(),
                    rectangle.getWidth(),
                    rectangle.getHeight()
                    );
            g2d.dispose();
        }
    }
    /// Narusuj linie na obrazie w podanym kolorze oraz odstępach mięczy nimi
    ///
    public void drawLine(LineModel line) {
        if (image != null) {
            int height = image.getHeight();
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(line.getColor());
            g2d.setStroke(new BasicStroke(2));
            for (int i = 0; i < height; i += line.getIntervals()) {
                g2d.drawLine(0, i, image.getWidth(), i);
            }
        }
    }

    /// Metody Tworzenia szarości obrazów
    ///
    ///

    /// Tworzy na podsawie średniej wartości kolorów
    public void setDilatation() {
        if (image != null){
           var imageArray = getPixelArrayFromImage(image);
           imageArray = DilataionOperations.createGrayPixelImageArray(imageArray);
           image = createImageFromPixelArray(imageArray);
        }
    }

    /// Tworzy na podstawie wart. koloru czerwonego
    public void trmakeGrayByRed(){
        if (image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = DilataionOperations.createGrayByRed(imageArray);

            image = createImageFromPixelArray(imageArray);
        }
    }
    /// Tworzy na podstawie wart. koloru Zielonego
    public void trmakeGrayByGreen(){
        if (image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = DilataionOperations.createGrayByGreen(imageArray);
            image = createImageFromPixelArray(imageArray);
        }
    }
    /// Tworzy na podstawie kolory Niebieskiego
    public void trmakeGrayByBlue(){
        if (image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = DilataionOperations.createGrayByBlue(imageArray);
            image = createImageFromPixelArray(imageArray);
        }
    }
    ///  Tworzy na podstawie modelu YUV
    public void trGrayByYUV(){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = DilataionOperations.createGrayByYUV(imageArray);
            image = createImageFromPixelArray(imageArray);
        }
    }
    ///
    /// Zmiana kontrastu oraz Jasności
    ///

    public void changeContrastAndBrightness(ContrAndBrightModel contrAndBrightModel){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);

            imageArray = PointFilterOperations.createContrAndBrightPixelImageArray(imageArray, contrAndBrightModel);
            image = createImageFromPixelArray(imageArray);
        }
    }
    ///
    /// Tworzy obraz w negatywie
    ///

    public void setNegativeImage(){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = PointFilterOperations.createNegativePixelImageArray(imageArray);

            image = createImageFromPixelArray(imageArray);
        }
    }
    ///
    /// Tworzy znomalizowany obraz
    ///

    public void setNormalizeBrightnessImage(){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = PointFilterOperations.createNormalizeBrightnesImageArray(imageArray);

            image = createImageFromPixelArray(imageArray);
        }
    }
    public void setGradientFilter(GradientModel gradientModel) {
        if (image != null) {
            var imageArray = getPixelArrayFromImage(image);
            imageArray = getPixelArrayFromImage(image);

            switch (gradientModel.getName()) {
                case "prosty":
                    imageArray = createImageArraySimpleGradient(imageArray);
                    break;
                case "roberts":
                    imageArray = createImageArrayRobertsGradient(imageArray);
                case "progowy":
                    imageArray = createImageArrayThresholdGradient(imageArray, gradientModel);
            }
            System.out.println(gradientModel.getName());
            image = createImageFromPixelArray(imageArray);
        }

    }
    private Pixel[][] createImageArraySimpleGradient(Pixel[][] imageArray){
        int width = imageArray.length;
        int height = imageArray[0].length;

        Pixel[][] newPixelArray = new Pixel[width][height];

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                // Oblicz gradient w poziomie (Gx) - różnica między prawym a lewym pikselem
                Pixel left = imageArray[x-1][y];
                Pixel right = imageArray[x+1][y];
                int gxRed = right.getRedPixel() - left.getRedPixel();
                int gxGreen = right.getGreenPixel() - left.getGreenPixel();
                int gxBlue = right.getBluePixel() - left.getBluePixel();

                // Oblicz gradient w pionie (Gy) - różnica między dolnym a górnym pikselem
                Pixel top = imageArray[x][y-1];
                Pixel bottom = imageArray[x][y+1];
                int gyRed = bottom.getRedPixel() - top.getRedPixel();
                int gyGreen = bottom.getGreenPixel() - top.getGreenPixel();
                int gyBlue = bottom.getBluePixel() - top.getBluePixel();

                // Oblicz moduł gradientu dla każdego kanału
                int gradientRed = (int) Math.sqrt(gxRed * gxRed + gyRed * gyRed);
                int gradientGreen = (int) Math.sqrt(gxGreen * gxGreen + gyGreen * gyGreen);
                int gradientBlue = (int) Math.sqrt(gxBlue * gxBlue + gyBlue * gyBlue);

                // Przycięcie wartości do zakresu 0-255
                newPixelArray[x][y] = new Pixel(
                        clamp(gradientRed),
                        clamp(gradientGreen),
                        clamp(gradientBlue)
                );
            }
        }

        fillBorders(newPixelArray);
        return newPixelArray;
    }
    private void fillBorders(Pixel[][] image) {
        int width = image.length;
        int height = image[0].length;
        Pixel black = new Pixel(0, 0, 0);

        for (int x = 0; x < width; x++) {
            image[x][0] = black;
            image[x][height-1] = black;
        }
        for (int y = 0; y < height; y++) {
            image[0][y] = black;
            image[width-1][y] = black;
        }
    }
    private Pixel[][] createImageArrayRobertsGradient(Pixel[][] imageArray){
        int width = imageArray.length;
        int height = imageArray[0].length;

        Pixel[][] newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width - 1; x++) {
                // Pobierz 4 piksele w układzie 2x2
                Pixel current = imageArray[x][y];
                Pixel right = imageArray[x+1][y];
                Pixel bottom = imageArray[x][y+1];
                Pixel bottomRight = imageArray[x+1][y+1];

                // Oblicz gradient Robertsa (G1 i G2) dla każdego kanału
                // G1 = bottomRight - current (przekątna główna)
                int g1Red = bottomRight.getRedPixel() - current.getRedPixel();
                int g1Green = bottomRight.getGreenPixel() - current.getGreenPixel();
                int g1Blue = bottomRight.getBluePixel() - current.getBluePixel();

                // G2 = bottom - right (przekątna antygłówna)
                int g2Red = bottom.getRedPixel() - right.getRedPixel();
                int g2Green = bottom.getGreenPixel() - right.getGreenPixel();
                int g2Blue = bottom.getBluePixel() - right.getBluePixel();

                // Oblicz moduł gradientu dla każdego kanału
                int gradientRed = (int) Math.sqrt(g1Red * g1Red + g2Red * g2Red);
                int gradientGreen = (int) Math.sqrt(g1Green * g1Green + g2Green * g2Green);
                int gradientBlue = (int) Math.sqrt(g1Blue * g1Blue + g2Blue * g2Blue);

                newPixelArray[x][y] = new Pixel(
                        clamp(gradientRed),
                        clamp(gradientGreen),
                        clamp(gradientBlue)
                );
            }
        }

        fillRobertsBorders(newPixelArray);
        return newPixelArray;
    }
    private void fillRobertsBorders(Pixel[][] image) {
        int width = image.length;
        int height = image[0].length;
        Pixel black = new Pixel(0, 0, 0);

        // Ostatnia kolumna
        for (int y = 0; y < height; y++) {
            image[width-1][y] = black;
        }
        // Ostatni wiersz
        for (int x = 0; x < width; x++) {
            image[x][height-1] = black;
        }
    }

    private Pixel[][] createImageArrayThresholdGradient(Pixel[][] imageArray, GradientModel gradientModel){
        imageArray = createImageArraySimpleGradient(imageArray);
        int mode = gradientModel.getMode();
        int threshold = gradientModel.getThershold();


        int width = imageArray.length;
        int height = imageArray[0].length;
        Pixel[][] result = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = imageArray[x][y];

                // Oblicz średnią wartość gradientu (dla uproszczenia używamy średniej z RGB)
                int gradientValue = (pixel.getRedPixel() + pixel.getGreenPixel() + pixel.getBluePixel()) / 3;

                switch (mode) {
                    case 1: // Wariant 1: Tło białe, reszta obrazu nieprzetworzona
                        result[x][y] = (gradientValue >= threshold) ? pixel : new Pixel(255, 255, 255);
                        break;

                    case 2: // Wariant 2: Krawędzie czarne, tło oryginalne
                        result[x][y] = (gradientValue >= threshold) ? new Pixel(0, 0, 0) : pixel;
                        break;

                    case 3: // Wariant 3: Czarne krawędzie na białym tle
                        result[x][y] = (gradientValue >= threshold) ? new Pixel(0, 0, 0) : new Pixel(255, 255, 255);
                        break;

                    default:
                        result[x][y] = pixel;
                }
            }
        }
        return result;
    }

    public void setStatisticFilter(String name){
      if(image != null) {
          var imageArray = getPixelArrayFromImage(image);

          switch (name){
              case "medianowy":
                  imageArray = createImageArrayWithMedianStatFilter(imageArray);
                  break;
              case "minimalny":
                  imageArray = createImageArrayWithMinimumStatFilter(imageArray);
                  break;
              case "maksymalny":
                  imageArray = createImageArrayWithMaximumStatFilter(imageArray);
                  break;
          }
          System.out.println(name);
          image = createImageFromPixelArray(imageArray);
      }
    }
    public void setSplitFilter(SplitFilterModel splitFilterModel){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = createImageArrayWithFilter(imageArray, splitFilterModel.getMartix());

            image = createImageFromPixelArray(imageArray);
        }
    }
    private Pixel[][] createImageArrayWithMedianStatFilter(Pixel[][] imageArray){
        int width = imageArray.length;       // Pierwszy wymiar to szerokość (x)
        int height = imageArray[0].length;   // Drugi wymiar to wysokość (y)

        Pixel[][] newPixelArray = new Pixel[width][height];

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                ArrayList<Integer> reds = new ArrayList<>();
                ArrayList<Integer> greens = new ArrayList<>();
                ArrayList<Integer> blues = new ArrayList<>();

                for (int fy = -1; fy <= 1; fy++) {
                    for (int fx = -1; fx <= 1; fx++) {
                        Pixel p = imageArray[x + fx][y + fy];
                        reds.add(p.getRedPixel());
                        greens.add(p.getGreenPixel());
                        blues.add(p.getBluePixel());
                    }
                }

                Collections.sort(reds);
                Collections.sort(greens);
                Collections.sort(blues);

                int mid = reds.size() / 2;
                newPixelArray[x][y] = new Pixel(reds.get(mid), greens.get(mid), blues.get(mid));
            }
        }
        copyBorders(imageArray, newPixelArray);
        return newPixelArray;
    }
    private Pixel[][] createImageArrayWithMinimumStatFilter(Pixel[][] imageArray){
        int width = imageArray.length;       // Pierwszy wymiar to szerokość (x)
        int height = imageArray[0].length;   // Drugi wymiar to wysokość (y)

        Pixel[][] newPixelArray = new Pixel[width][height];

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int minR = 255, minG = 255, minB = 255;

                for (int fy = -1; fy <= 1; fy++) {
                    for (int fx = -1; fx <= 1; fx++) {
                        Pixel p = imageArray[x + fx][y + fy];
                        minR = Math.min(minR, p.getRedPixel());
                        minG = Math.min(minG, p.getGreenPixel());
                        minB = Math.min(minB, p.getBluePixel());
                    }
                }

                newPixelArray[x][y] = new Pixel(minR, minG, minB);
            }
        }
        copyBorders(imageArray,newPixelArray);
        return newPixelArray;
    }
    private Pixel[][] createImageArrayWithMaximumStatFilter(Pixel[][] imageArray){
        int width = imageArray.length;       // Pierwszy wymiar to szerokość (x)
        int height = imageArray[0].length;   // Drugi wymiar to wysokość (y)

        Pixel[][] newPixelArray = new Pixel[width][height];

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int maxR = 0, maxG = 0, maxB = 0;

                for (int fy = -1; fy <= 1; fy++) {
                    for (int fx = -1; fx <= 1; fx++) {
                        Pixel p = imageArray[x + fx][y + fy];
                        maxR = Math.max(maxR, p.getRedPixel());
                        maxG = Math.max(maxG, p.getGreenPixel());
                        maxB = Math.max(maxB, p.getBluePixel());
                    }
                }

                newPixelArray[x][y] = new Pixel(maxR, maxG, maxB);
            }
        }
        copyBorders(imageArray,newPixelArray);
        return newPixelArray;
    }



    private Pixel[][] createImageArrayWithFilter(Pixel[][] imageArray, float[][] filterMatrix) {
        int width = imageArray.length;       // Pierwszy wymiar to szerokość (x)
        int height = imageArray[0].length;   // Drugi wymiar to wysokość (y)

        Pixel[][] newPixelArray = new Pixel[width][height];

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                float sumR = 0, sumG = 0, sumB = 0;

                for (int fy = 0; fy < 3; fy++) {
                    for (int fx = 0; fx < 3; fx++) {
                        int imageX = x + fx - 1;
                        int imageY = y + fy - 1;

                        Pixel neighbor = imageArray[imageX][imageY];  // UWAGA: imageX przed imageY!
                        float weight = filterMatrix[fy][fx];

                        sumR += neighbor.getRedPixel() * weight;
                        sumG += neighbor.getGreenPixel() * weight;
                        sumB += neighbor.getBluePixel() * weight;
                    }
                }

                newPixelArray[x][y] = new Pixel(
                        clamp((int) sumR),
                        clamp((int) sumG),
                        clamp((int) sumB)
                );
            }
        }

        copyBorders(imageArray, newPixelArray);
        return newPixelArray;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
    private void copyBorders(Pixel[][] source, Pixel[][] destination) {
        int width = source.length;
        int height = source[0].length;

        // Kopiowanie wszystkich pikseli brzegowych
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || x == width-1 || y == 0 || y == height-1) {
                    destination[x][y] = source[x][y];
                }
            }
        }
    }

    ///
    ///     Pobiera obraz oraz zwraca go w dwuwumiarowej tabeli Pixeli
    ///

    private Pixel[][] getPixelArrayFromImage(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();
        Pixel[][] pixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x,y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                pixelArray[x][y] = new Pixel(r,g,b);
            }
        }
        return pixelArray;
    }
///
/// Pobiera Dwuwymiarową tabelę pixeli oraz zwraca obraz
///
    private BufferedImage createImageFromPixelArray(Pixel[][] pixelArray){
        int width = pixelArray.length;
        int height = pixelArray[0].length;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = pixelArray[x][y];
                int newRGB = (pixel.getRedPixel() << 16) | (pixel.getGreenPixel() << 8) | pixel.getBluePixel();
                image.setRGB(x,y,newRGB);
            }
        } return image;
    }

    ///
    /// Wyświtla w konsoli wartości wszystkiech pixeli z tablicy dwuwymiarowej
    ///
    private void displayPixelArray(Pixel[][] pixels) {
        // Pobierz wymiary tablicy
        int width = pixels.length;
        int height = pixels[0].length;

        // Przejdź przez wszystkie piksele
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Pobierz obiekt Pixel
                Pixel pixel = pixels[x][y];

                // Wyświetl wartości R, G, B
                System.out.printf("Pixel (%d, %d): R=%d, G=%d, B=%d\n",
                        x, y, pixel.getRedPixel(), pixel.getGreenPixel(), pixel.getBluePixel());
            }
        }
    }
}


