package controllers;

import models.*;
import views.ImagePanel;
import views.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Kontroler odpowiedzialny za zarządzanie operacjami na obrazach w aplikacji.
 * Obsługuje wczytywanie, czyszczenie, kopiowanie oraz rysowanie kształtów na obrazach.
 */
public class ImageController {

    private final MainFrame mainFrame;

    private final ImagePanel leftPanel;

    private final ImagePanel rightPanel;

    public ImageController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.leftPanel = mainFrame.getLeftPanel();
        this.rightPanel = mainFrame.getRightPanel();
    }

    /**
     * Wczytuje obraz z pliku i ustawia go w lewym panelu.
     *
     * @param file Plik obrazu do wczytania.
     */
    public void loadImage(File file) {
        try {
            var image = ImageIO.read(file);

            var model = new ImageModel(image);
            leftPanel.setModel(model);
            leftPanel.repaint();

            mainFrame.adjustWindowSize(); // Dopasowanie rozmiaru okna po załadowaniu obrazu
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Nieznany błąd!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Usuwa obraz z lewego panelu.
     */
    public void clearLeftPanel() {
        leftPanel.setModel(null);
        leftPanel.repaint();
    }

    /**
     * Usuwa obraz z prawego panelu.
     */
    public void clearRightPanel() {
        rightPanel.setModel(null);
        rightPanel.repaint();
    }

    /**
     * Kopiuje obraz z lewego panelu do prawego panelu.
     * Jeśli lewy panel nie zawiera obrazu, operacja nie jest wykonywana.
     */
    public void copyLeftPanel() {
        if (leftPanel.getModel() == null) {
            return;
        }

        // Utworzenie kopii obrazu z lewego panelu, aby modyfikacje nie wpłynęły na oryginalny obraz.
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);

        rightPanel.setModel(model);
        rightPanel.repaint();
    }

    /**
     * Kopiuje obraz z prawego panelu do lewego panelu.
     */
    public void copyRightPanel() {
            if (rightPanel.getModel() == null) {
                return;
            }

            // Utworzenie kopii obrazu z lewego panelu, aby modyfikacje nie wpłynęły na oryginalny obraz.
            var image = rightPanel.getModel().getCopyImage();
            var model = new ImageModel(image);

            leftPanel.setModel(model);
            leftPanel.repaint();
    }

    /**
     * Rysuje koło na obrazie znajdującym się w lewym panelu i umieszcza wynik w prawym panelu.
     * Jeśli lewy panel nie zawiera obrazu, wyświetlane jest komunikat z błędem.
     *
     * @param circle Model kola.
     */
    public void drawCircle(CircleModel circle) {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        var image = leftPanel.getModel().getCopyImage(); // Utworzenie kopii obrazu z lewego panelu

        var model = new ImageModel(image); // Nowa instancje modelu, utworzona z obrazem z panelu lewego.
        model.drawCircle(circle); // Modyfikacja modelu. Narysowanie koła na skopiowany obrazie.

        rightPanel.setModel(model); // Ustawienie zmodyfikowanego modelu w prawym panelu.

        rightPanel.repaint(); // Ponownie narysowanie komponentu.
    }

    /**
     * Rysuje prostokąt na obrazie znajdującym się w lewym panelu i umieszcza wynik w prawym panelu.
     * Jeśli lewy panel nie zawiera obrazu, operacja nie jest wykonywana.
     *
     * @param rectangle Model prostokąta do narysowania.
     */
    public void drawRectangle(RectangleModel rectangle) {
        // TODO: Zaimplementować rysowanie prostokąta na obrazie.
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        var image = leftPanel.getModel().getCopyImage(); // Utworzenie kopii obrazu z lewego panelu

        var model = new ImageModel(image); // Nowa instancje modelu, utworzona z obrazem z panelu lewego.
        model.drawRectangle(rectangle); // Modyfikacja modelu. Narysowanie koła na skopiowany obrazie.

        rightPanel.setModel(model); // Ustawienie zmodyfikowanego modelu w prawym panelu.

        rightPanel.repaint(); // Ponownie
    }

    public void addFilter(FilterModel filterModel){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadownego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);






        rightPanel.setModel(model);
        rightPanel.repaint();
    }



    public void drawline(LineModel line){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadownego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.drawLine(line);

        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void transformDilataion(){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.setDilatation();

        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void makeGrayByRed(){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.trmakeGrayByRed();

        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void makeGrayByGreen(){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.trmakeGrayByGreen();

        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void makeGrayByBlue(){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.trmakeGrayByBlue();

        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void makeGrayByYUV(){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.trGrayByYUV();

        rightPanel.setModel(model);
        rightPanel.repaint();
    }




    public void changeContrastAndBrightness(ContrAndBrightModel contrAndBrightModel){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.changeContrastAndBrightness(contrAndBrightModel);


        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void transformNegative(){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.setNegativeImage();

        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void normalizeBrightness(){
        if(leftPanel.getModel() == null || leftPanel.getModel().getImage() == null){
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!","Błąd",JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);

        model.setNormalizeBrightnessImage();

        rightPanel.setModel(model);
        rightPanel.repaint();
    }
}
