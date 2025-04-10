package models;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Model reprezentujący obraz, umożliwiający jego modyfikację oraz kopiowanie.
 *
 * <p>
 *     Przechowuje obraz w postaci obiektu {@link BufferedImage} i udostępnia metody do jego modyfikacji.
 *     Tylko ta klasa powinna udostępniać metody do modyfikacji obrazu.
 * </p>
 *
 */
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

    /**
     * Tworzy i zwraca kopię obrazu. Nowy obraz jest kopiowany piksel po pikselu
     *
     * @return Kopia obrazu jako {@link BufferedImage}.
     */
    public BufferedImage getCopyImage() {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return copy;
    }

    /**
     * Rysuje koło na obrazie na podstawie modelu {@link CircleModel}. Koło jest rysowane w miejscu określonym przez współrzędne środka i promień.
     *
     * <p>Iteracyjna implementacja metody:</p>
     * <pre>
     * {@code
     *      int imgWidth = image.getWidth();
     *      int imgHeight = image.getHeight();
     *      int centerX = circle.getCenterX();
     *      int centerY = circle.getCenterY();
     *      int radius = circle.getRadius();
     *      int radiusSquared = radius * radius;
     *      int colorRGB = circle.getColor().getRGB();
     *
     *      for (int y = Math.max(centerY - radius, 0); y < Math.min(centerY + radius, imgHeight); y++) {
     *          for (int x = Math.max(centerX - radius, 0); x < Math.min(centerX + radius, imgWidth); x++) {
     *              int dx = x - centerX;
     *              int dy = y - centerY;
     *              if (dx * dx + dy * dy <= radiusSquared) { // Sprawdzamy, czy punkt leży w kole
     *                  image.setRGB(x, y, colorRGB);
     *              }
     *          }
     *      }
     * }
     * </pre>
     *
     *
     * @param circle Obiekt klasy {@link CircleModel}, określający parametry koła.
     */
    public void drawCircle(CircleModel circle) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(circle.getColor());
            g2d.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
            g2d.dispose();
        }
    }

    /**
     * Rysuje prostokąt na obrazie na podstawie modelu {@link RectangleModel}.
     *
     * @param rectangle Obiekt klasy {@link RectangleModel}, określający parametry prostokąta.
     */
    public void drawRectangle(RectangleModel rectangle) {
        // TODO: Implementacja metody rysowania prostokąta.
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
    public void setDilatation() {
        if (image != null){
           var imageArray = getPixelArrayFromImage(image);
           imageArray = createGrayPixelImageArray(imageArray);
           image = createImageFromPixelArray(imageArray);
        }
    }
    public void trmakeGrayByRed(){
        if (image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = createGrayByRed(imageArray);
            image = createImageFromPixelArray(imageArray);
        }
    }
    public void trmakeGrayByGreen(){
        if (image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = createGrayByGreen(imageArray);
            image = createImageFromPixelArray(imageArray);
        }
    } public void trmakeGrayByBlue(){
        if (image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = createGrayByBlue(imageArray);
            image = createImageFromPixelArray(imageArray);
        }
    }
    public void trGrayByYUV(){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);

            imageArray = createGrayByYUV(imageArray);
            image = createImageFromPixelArray(imageArray);
        }
    }
    public void changeContrastAndBrightness(ContrAndBrightModel contrAndBrightModel){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);

            imageArray = createContrAndBrightPixelImageArray(imageArray, contrAndBrightModel);
            image = createImageFromPixelArray(imageArray);
        }
    }
    public void setNegativeImage(){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = createNegativePixelImageArray(imageArray);

            image = createImageFromPixelArray(imageArray);
        }
    }
    public void setNormalizeBrightnessImage(){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = createNormalizeBrightnesImageArray(imageArray);

            image = createImageFromPixelArray(imageArray);
        }
    }
    public void addFilter(float[][] filterArray){
        if(image != null){
            var imageArray = getPixelArrayFromImage(image);
            imageArray = createArrayWithFilter(imageArray,filterArray);


            image = createImageFromPixelArray(imageArray);
        }
    }




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
    private Pixel[][] createArrayWithFilter(Pixel[][] pixelArray, float[][] filterMatrix){
        if (filterMatrix.length != 3 || filterMatrix[0].length != 3) {
            throw new IllegalArgumentException("Macierz filtra musi być 3x3");
        }

        int width = pixelArray.length;
        int height = pixelArray[0].length;
        var newPixelArray = new Pixel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                float sumR = 0, sumG = 0, sumB = 0;

                // Przechodzimy przez sąsiedztwo 3x3
                for (int fy = 0; fy < 3; fy++) {
                    for (int fx = 0; fx < 3; fx++) {
                        int imageX = x + fx - 1;
                        int imageY = y + fy - 1;

                        Pixel neighbor = pixelArray[imageX][imageY];
                        float weight = filterMatrix[fy][fx];

                        sumR += neighbor.getRedPixel() * weight;
                        sumG += neighbor.getGreenPixel() * weight;
                        sumB += neighbor.getBluePixel() * weight;
                    }
                }

                // Przycinanie wartości do zakresu 0-255
                int r = clamp((int) sumR);
                int g = clamp((int) sumG);
                int b = clamp((int) sumB);

                newPixelArray[x][y] = new Pixel(r, g, b);

                newPixelArray[x][y] = new Pixel(r,g,b);
            }
        }

        return newPixelArray;
    }
    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }



    private Pixel[][] createGrayPixelImageArray(Pixel[][] pixelArray){
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
        }
        return newPixelArray;
    }
    private Pixel[][] createGrayByRed(Pixel[][] pixelArray){
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
    private Pixel[][] createGrayByGreen(Pixel[][] pixelArray){
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
    private Pixel[][] createGrayByBlue(Pixel[][] pixelArray){
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
    private Pixel[][] createGrayByYUV(Pixel[][] pixelArray){
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
    private Pixel[][] createNormalizeBrightnesImageArray(Pixel[][] pixelArray){
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

    private Pixel[][] createNegativePixelImageArray(Pixel[][] pixelArray){
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

// Zawiera algorytm który oblicza nowe wartości dla skłądowych pikseli, pobierając tablice oraz


    private Pixel[][] createContrAndBrightPixelImageArray(Pixel[][] pixelArray, ContrAndBrightModel contrAndBrightModel){
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



// Wyświetla wartość RGB każdego piksela który jest zapisany w tablicy pixeli


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


