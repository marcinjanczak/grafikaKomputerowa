package views.dialogs;

import views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeCurveDialog extends JDialog {
        private List<Point> selectedPoints = new ArrayList<>();
        private BufferedImage image;

        public MakeCurveDialog(JFrame parent) {
            super(parent,"Wybierz punkty", true);
            setSize(300,200);
            setLocationRelativeTo(parent);
            setLayout(new GridLayout(2,2));

            JPanel imagePanel = getMainPanel(parent);
            add(imagePanel);

            JPanel pointsArea = getPointsArea();
            add(pointsArea);

            JPanel maniPulationPanel = getPointManipulationPanel();
            add(maniPulationPanel);

            JPanel buttonPanel = getButtonpanel();
            add(buttonPanel);
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
        private void actionListerens(){

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
            okButton.addActionListener(e -> getPoints());

            panel.add(okButton);
            panel.add(cancelButton);

            return panel;
        }
        private JPanel getPointsArea(){
            var panel = new JPanel();
            JTextArea pointsArea = new JTextArea();


            panel.add(pointsArea);

            return panel;
        }
        private JPanel getPointManipulationPanel(){
            var panel = new JPanel(new GridLayout(3,1,10,10));
            JButton scaleButton = new JButton("Skaluj");
            JButton moveButton = new JButton("Przesuń");
            JButton rotateButton = new JButton("Obróć");

            panel.add(scaleButton);
            panel.add(moveButton);
            panel.add(rotateButton);

            return panel;
        }
        public List<Point> getPoints(){
            for (Point points : selectedPoints){
                System.out.println(points);
            }
            return selectedPoints;
        }
    }
