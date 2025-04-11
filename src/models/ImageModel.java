package models;

import models.imageOperations.DilataionOperations;
import models.imageOperations.PointFilterOperations;

import java.awt.*;
import java.awt.image.BufferedImage;

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


