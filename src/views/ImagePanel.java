package views;

import models.ImageModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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

    public static class MakeCurveDialog extends JDialog{
        private List<Point> selectedPoints = new ArrayList<>();
        private BufferedImage image;

        public MakeCurveDialog(JFrame parent) {
            super(parent,"Wybierz punkty", true);
            setSize(300,200);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());

            JPanel panel = getMainPanel(parent);
            add(panel,BorderLayout.CENTER);

            JPanel buttonPanel = getButtonpanel();
            add(buttonPanel,BorderLayout.SOUTH);
        }
        private JPanel getMainPanel(JFrame parent) {
            this.image = ((MainFrame)parent).getLeftPanel().getModel().getImage();

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    if (image != null) {
                        // Rysowanie obrazu
                        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

                        // Rysowanie punktów
                        g.setColor(Color.RED);
                        for (Point p : selectedPoints) {
                            g.fillOval(p.x-5, p.y-5, 10, 10);
                        }
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setColor(Color.BLACK);
                        g.drawString("Wczytaj najpierw obraz!", 50, 50);
                    }
                }
            };
            adjustWindowSize(image);

            // Obsługa myszy
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (image != null) {
                        selectedPoints.add(e.getPoint());
                        panel.repaint();
                    }
                }
            });

            return panel;
        }
        public void adjustWindowSize(BufferedImage image) {
            if (image == null) {
                return;
            }
            int newWidth = Math.max(getWidth(), image.getWidth() + 100);

            int newHeight = Math.max(getHeight(), image.getHeight() + 100);
            setSize(newWidth, newHeight);
            setLocationRelativeTo(null);
        }

        private JPanel getButtonpanel(){
            var panel = new JPanel();

            JButton okButton = new JButton("Narysuj");
            JButton cancelButton = new JButton("Anuluj");

            cancelButton.addActionListener(e-> dispose());

            panel.add(okButton);
            panel.add(cancelButton);

            return panel;
        }
    }
}