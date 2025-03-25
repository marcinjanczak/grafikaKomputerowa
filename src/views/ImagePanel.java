package views;

import models.ImageModel;

import javax.swing.*;
import java.awt.*;

/**
 * Panel do wyświetlania obrazu, wykorzystujący model {@link ImageModel}.
 * Panel odświeża się automatycznie po zmianie modelu.
 */
public class ImagePanel extends JPanel {

    private ImageModel model;

    public ImagePanel(String title) {
        setBorder(BorderFactory.createTitledBorder(title)); // Ustawienie obramowania z tytułem
        setBackground(Color.LIGHT_GRAY); // Ustawienie koloru tła panelu
        this.model = null;
    }

    /**
     * Ustawia nowy model obrazu i odświeża panel.
     * @param model Nowy model obrazu do wyświetlenia.
     */
    public void setModel(ImageModel model) {
        this.model = model;
        this.repaint(); // Odświeżenie panelu, aby wyświetlić nowy obraz
    }

    public ImageModel getModel() {
        return model;
    }

    /**
     * Przesłonięta metoda rysowania komponentu. Wyświetla obraz na środku panelu.
     * @param g Kontekst graficzny wykorzystywany do rysowania obrazu.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (model != null && model.getImage() != null) {
            // Obliczenie współrzędnych, aby obraz był wyśrodkowany w panelu
            int x = (getWidth() - model.getImage().getWidth()) / 2;
            int y = (getHeight() -  model.getImage().getHeight()) / 2;

            // Rysowanie obrazu na panelu
            g.drawImage( model.getImage(), x, y, this);
        }
    }
}