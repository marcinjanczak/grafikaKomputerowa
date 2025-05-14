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
            setSize(800,600);
            setLocationRelativeTo(parent);
            setLayout(new GridBagLayout());

            JPanel imagePanel = getMainPanel(parent);
            JPanel pointsArea = getPointsArea();
            JPanel maniPulationPanel = getPointManipulationPanel();
            JPanel buttonPanel = getButtonpanel();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 9; // Zajmuje 3/4 szerokości
            gbc.gridheight = 9; // Zajmuje 3/4 wysokości
            gbc.weightx = 0.9; // Waga szerokości
            gbc.weighty = 0.9; // Waga wysokości

            add(imagePanel,gbc);

            gbc.gridx = 9;
            gbc.gridy = 0;
            gbc.gridwidth = 1; // Zajmuje 1/4 szerokości
            gbc.gridheight = 9; // Zajmuje 3/4 wysokości
            gbc.weightx = 0.10;
            gbc.weighty = 0.9;
            add(pointsArea,gbc);


            gbc.gridx = 0;
            gbc.gridy = 9;
            gbc.gridwidth = 9; // 3/4 szerokości
            gbc.gridheight = 1; // 1/4 wysokości
            gbc.weightx = 0.9;
            gbc.weighty = 0.1;
            add(maniPulationPanel,gbc);

            gbc.gridx = 9;
            gbc.gridy = 9;
            gbc.gridwidth = 1; // 1/4 szerokości
            gbc.gridheight = 1; // 1/4 wysokości
            gbc.weightx = 0.1;
            gbc.weighty = 0.1;
            add(buttonPanel,gbc);
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
            int newWidth = Math.max(getWidth(), image.getWidth() + 300);

            int newHeight = Math.max(getHeight(), image.getHeight() + 300);
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
            JButton editPoint = new JButton("Edutuj punkt");
            JButton deletePoint = new JButton("Usuń punkt");

            JTextArea pointsArea = new JTextArea();

            panel.add(editPoint);
            panel.add(deletePoint);
            panel.add(pointsArea);

            return panel;
        }
        private JPanel getPointManipulationPanel(){
            var finalpanel = new JPanel(new GridLayout(1,3));


            var manipulationPanel = new JPanel(new GridLayout(3,3,10,10));
            JButton scaleButton = new JButton("Skaluj");
            JTextField scaleXField = new JTextField();
            JTextField scaleYField = new JTextField();

            JButton moveButton = new JButton("Przesuń");
            JTextField moveXField = new JTextField();
            JTextField moveYField = new JTextField();

            JButton rotateButton = new JButton("Obróć");
            JTextField rotateXField = new JTextField();
            JTextField rotateYField = new JTextField();

            manipulationPanel.add(scaleButton);
            manipulationPanel.add(scaleXField);
            manipulationPanel.add(scaleYField);

            manipulationPanel.add(moveButton);
            manipulationPanel.add(moveXField);
            manipulationPanel.add(moveYField);

            manipulationPanel.add(rotateButton);
            manipulationPanel.add(rotateXField);
            manipulationPanel.add(rotateYField);

            var matrixPanel = new JPanel(new GridLayout(3,3));

            var curvePanel = new JPanel(new GridLayout(1,2));


            finalpanel.add(manipulationPanel);
            finalpanel.add(matrixPanel);
            finalpanel.add(curvePanel);

            return finalpanel;
        }
        public List<Point> getPoints(){
            for (Point points : selectedPoints){
                System.out.println(points);
            }
            return selectedPoints;
        }
    }
