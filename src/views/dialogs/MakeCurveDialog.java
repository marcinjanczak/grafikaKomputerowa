package views.dialogs;

import models.BezierDrawer;
import models.ImageModel;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MakeCurveDialog extends JDialog {
    JTextField scaleXField;
    JTextField scaleYField;
    JTextField moveYField;
    JTextField moveXField;
    JTextField rotateField;
    private BufferedImage image;
    private BezierDrawer bezierDrawer;
    private JList<Point> pointsList;
    private List<Point> originalPoints = new ArrayList<>();
    private List<Point> selectedPoints = new ArrayList<>();
    private List<Point> matrixPoints = new ArrayList<>();
    private DefaultListModel<Point> listModel;
    private JTextField curveStepsFiield;
    private JTextArea matrixDisplay;
    private JButton makeCurveButton;
    private JButton cancelButton;
    private JButton applyButton;
    private double[][] manipulationMatrix;
    private boolean drawCurve;


    public MakeCurveDialog(JFrame parent) {
        super(parent, "Wybierz punkty", true);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1000, 800));

        listModel = new DefaultListModel<>();
        pointsList = new JList<>(listModel);
        bezierDrawer = new BezierDrawer();

        manipulationMatrix = defaultMatrix();

        JPanel imagePanel = getMainPanel(parent);
        JPanel pointsArea = getPointsArea();
        JPanel maniPulationPanel = getPointManipulationPanel();
        JPanel buttonPanel = getButtonpanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 9;
        gbc.gridheight = 9;
        gbc.weightx = 0.9;
        gbc.weighty = 0.9;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        add(imagePanel, gbc);

        gbc.gridx = 9;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 9;
        gbc.weightx = 0.10;
        gbc.weighty = 0.9;
        add(pointsArea, gbc);


        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 9; // 3/4 szerokości
        gbc.gridheight = 1; // 1/4 wysokości
        gbc.weightx = 0.9;
        gbc.weighty = 0.1;
        add(maniPulationPanel, gbc);

        gbc.gridx = 9;
        gbc.gridy = 9;
        gbc.gridwidth = 1; // 1/4 szerokości
        gbc.gridheight = 1; // 1/4 wysokości
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        add(buttonPanel, gbc);

        pack();
//        autoAdjustWindowSize();
        if (image != null) {
            int width = Math.max(1000, image.getWidth() + 400);
            int height = Math.max(800, image.getHeight() + 200);
            setSize(width, height);
        }
        setLocationRelativeTo(parent);
    }

    private JPanel getMainPanel(JFrame parent) {
        this.image = ((MainFrame) parent).getLeftPanel().getModel().getImage();

        JPanel panel = new JPanel() {
            private int imgX = 0;
            private int imgY = 0;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {

                    ///  Obliczanie środka panelu.
                    imgX = (getWidth() - image.getWidth()) / 2;
                    imgY = (getHeight() - image.getHeight()) / 2;

                    ///  Ustawienie tła obrazu na kolor szary
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, getWidth(), getHeight());

                    /// Rysowanie obrazu na srodku panelu
                    g.drawImage(image, imgX, imgY, this);

                    ///  Rysowanie punktów między punktami jeżeli ich ilość jest większa od 2
                    if (selectedPoints.size() > 1) {
                        g.setColor(Color.RED);
                        Point prev = selectedPoints.get(0);
                        for (int i = 1; i < selectedPoints.size(); i++) {
                            Point current = selectedPoints.get(i);
                            g.drawLine(imgX + prev.x, imgY + prev.y,
                                    imgX + current.x, imgY + current.y);
                            prev = current;
                        }
                    }
                    /// Rysowanie punktów
                    g.setColor(Color.RED);
                    for (Point p : selectedPoints) {
                        g.fillOval(imgX + p.x - 5, imgY + p.y - 5, 10, 10);
                    }

                    ///  Rysowanie lini krzywej beziera na podstawie wyliconych kolejno punktów.
                    if (drawCurve && selectedPoints.size() >= 2) {
                        g.setColor(Color.BLUE);
                        bezierDrawer.drawBezier(g, getPoints(), parseIntField(curveStepsFiield),
                                imgX, imgY);
                    }
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("Wczytaj najpierw obraz!", 50, 50);
                }
            }

            private boolean isPointInImage(Point p) {
                return image != null && p.x >= 0 && p.y >= 0
                        && p.x < image.getWidth() && p.y < image.getHeight();
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (image != null) {
                    int xOnImage = e.getX() - (panel.getWidth() - image.getWidth()) / 2;
                    int yOnImage = e.getY() - (panel.getHeight() - image.getHeight()) / 2;

                    if (xOnImage >= 0 && yOnImage >= 0 && xOnImage < image.getWidth() && yOnImage < image.getHeight()) {
                        int selectedIndex = pointsList.getSelectedIndex();
                        if (selectedIndex != -1) {
                            Point p = originalPoints.get(selectedIndex);
                            p.setLocation(xOnImage, yOnImage);
                            listModel.set(selectedIndex, p);
                        } else {
                            Point newPoint = new Point(xOnImage, yOnImage);
                            originalPoints.add(newPoint);
                            listModel.addElement(newPoint);
                        }
                        selectedPoints = bezierDrawer.calculateNewPoints(manipulationMatrix, originalPoints);
                        panel.repaint();
                    }
                }
            }
        });

        pointsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = pointsList.locationToIndex(e.getPoint());
                    if (index == -1 || !pointsList.getCellBounds(index, index).contains((e.getPoint()))) {
                        pointsList.clearSelection();
                    }
                }
            }
        });
        return panel;
    }


    private JPanel getButtonpanel() {
        var panel = new JPanel(new GridLayout(5, 1, 20, 20));

        curveStepsFiield = new JTextField("100");
        makeCurveButton = new JButton("Narysuj Krzywą");
        cancelButton = new JButton("Zakończ");
        applyButton = new JButton("Zastosuj");

        makeCurveButton.addActionListener(e -> {
            drawCurve = true;
            repaint();
        });

        cancelButton.addActionListener(e -> dispose());
        applyButton.addActionListener(e -> {
            // Tworzymy kopię obrazu z naniesionymi krzywymi
            BufferedImage resultImage = createImageWithCurves();

            // Pobieramy referencję do MainFrame
            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);

            // Ustawiamy nowy model z obrazem w lewym panelu
            mainFrame.getRightPanel().setModel(new ImageModel(resultImage));
            mainFrame.getRightPanel().repaint();

            dispose(); // Zamykamy dialog
        });

        panel.add(new JLabel("Podaj dokładność krzywej"));
        panel.add(curveStepsFiield);
        panel.add(makeCurveButton);
        panel.add(cancelButton);
        panel.add(applyButton);


        return panel;
    }

    private JPanel getPointsArea() {
        JPanel panel = new JPanel(new BorderLayout());

        pointsList.setCellRenderer(new PointListRenderer());
        JScrollPane scrollPane = new JScrollPane(pointsList);
        panel.add(scrollPane, BorderLayout.CENTER);


        JButton editPointButton = new JButton("Edutuj punkt");
        JButton deletePointButton = new JButton("Usuń punkt");
        var buttonPanel = new JPanel(new FlowLayout());
//        buttonPanel.add(editPointButton);
        buttonPanel.add(deletePointButton);

        pointsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                editPointButton.setEnabled(pointsList.getSelectedIndex() != -1);
                deletePointButton.setEnabled(pointsList.getSelectedIndex() != -1);
            }
        });

        editPointButton.addActionListener(e -> {
            int selectedIndex = pointsList.getSelectedIndex();
            if (selectedIndex != -1) {
                Point p = selectedPoints.get(selectedIndex);
                String input = JOptionPane.showInputDialog(
                        this,
                        "edytuj punkt (format: x,y):",
                        p.x + ',' + p.y
                );
                if (input != null) {
                    String[] coords = input.split(",");
                    if (coords.length == 2) {
                        p.x = Integer.parseInt(coords[0]);
                        p.y = Integer.parseInt(coords[1]);
                        listModel.set(selectedIndex, p);
                        repaint();
                    }
                }
            }
        });
        deletePointButton.addActionListener(e -> {
            int selectedIndex = pointsList.getSelectedIndex();
            if (selectedIndex != -1) {
                selectedPoints.remove(selectedIndex);
                listModel.remove(selectedIndex);
                repaint();
            }
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);

        selectedPoints.forEach(listModel::addElement);

        return panel;
    }

    private JPanel getPointManipulationPanel() {
        var finalpanel = new JPanel(new GridLayout(1, 2));

        var manipulationPanel = new JPanel(new GridLayout(3, 6, 10, 10));
        JButton scaleButton = new JButton("Skaluj");
        scaleXField = new JTextField();
        scaleYField = new JTextField();

        JButton moveButton = new JButton("Przesuń");
        moveXField = new JTextField();
        moveYField = new JTextField();

        JButton rotateButton = new JButton("Obróć");
        rotateField = new JTextField();

        manipulationPanel.add(scaleButton);
        manipulationPanel.add(scaleXField);
        manipulationPanel.add(scaleYField);

        manipulationPanel.add(moveButton);
        manipulationPanel.add(moveXField);
        manipulationPanel.add(moveYField);

        manipulationPanel.add(rotateButton);
        manipulationPanel.add(rotateField);
        manipulationPanel.add(new JLabel(""));


        scaleButton.addActionListener(e -> {
            setScale();
            clearManipulationFields();
        });
        moveButton.addActionListener(e -> {
            setMove();
            clearManipulationFields();
        });
        rotateButton.addActionListener(e -> {
            setRotate();
            clearManipulationFields();
        });

        var matrixPanel = new JPanel(new BorderLayout());

        matrixPanel.setBorder(BorderFactory.createTitledBorder("Macierz transformacji"));

        matrixDisplay = new JTextArea(3, 20);
        matrixDisplay.setEditable(false);
        matrixDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        matrixPanel.add(new JScrollPane(matrixDisplay), BorderLayout.CENTER);

        updateMatrixDisplay();

        finalpanel.add(manipulationPanel);
        finalpanel.add(matrixPanel);

        return finalpanel;
    }

    private void clearManipulationFields() {
        scaleXField.setText("");
        scaleYField.setText("");

        moveXField.setText("");
        moveYField.setText("");
        rotateField.setText("");
    }

    private double[][] defaultMatrix() {
        return new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
    }

    private void setScale() {

        double xValue = parseDoubleField(scaleXField);
        double yValue = parseDoubleField(scaleYField);

        double[][] scalematrix = bezierDrawer.createScaleMatrix(xValue, yValue);
        manipulationMatrix = bezierDrawer.multiplyMatrices(manipulationMatrix, scalematrix);
        updateMatrixDisplay();
        List<Point> newPoints = bezierDrawer.calculateNewPoints(manipulationMatrix, getPoints());

        selectedPoints.clear();
        selectedPoints.addAll(newPoints);
//        updateListModel(newPoints);
        repaint();

    }

    private void setMove() {
        double xValue = parseDoubleField(moveXField);
        double yValue = parseDoubleField(moveYField);
        double[][] moveMatrix = bezierDrawer.createMoveMatrix(xValue, yValue);

        manipulationMatrix = bezierDrawer.multiplyMatrices(manipulationMatrix, moveMatrix);

        updateMatrixDisplay();
        List<Point> newPoints = bezierDrawer.calculateNewPoints(manipulationMatrix, getPoints());

        selectedPoints.clear();
        selectedPoints.addAll(newPoints);

//        updateListModel(newPoints);
//        setSelectedPoints(newPoints);
        repaint();
    }

    private void setRotate() {
        double rotateValue = parseDoubleField(rotateField);
        double[][] rotateMatrix = bezierDrawer.createRotateMatrix(rotateValue);

        manipulationMatrix = bezierDrawer.multiplyMatrices(manipulationMatrix, rotateMatrix);

        updateMatrixDisplay();
        List<Point> newPoints = bezierDrawer.calculateNewPoints(manipulationMatrix, getPoints());

        selectedPoints.clear();
        selectedPoints.addAll(newPoints);

//        updateListModel(newPoints);
//        setSelectedPoints(newPoints);
        repaint();
    }

    private void updateListModel(List<Point> points) {
        listModel.clear();
        for (Point p : points) {
            listModel.addElement(p);
        }
    }

    private void updateMatrixDisplay() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat();

        for (int i = 0; i < 3; i++) {
            sb.append("| ");
            for (int j = 0; j < 3; j++) {
                sb.append(String.format("%6s", df.format(manipulationMatrix[i][j])));
                sb.append(" ");
            }
            sb.append("|\n");
        }
        matrixDisplay.setText(sb.toString());
    }

    private double parseDoubleField(JTextField field) {
        return Double.parseDouble(field.getText());
    }

    private Integer parseIntField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BufferedImage createImageWithCurves() {
        if (image == null) return null;
        BufferedImage result = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        // Rysujemy oryginalny obraz
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(image, 0, 0, null);

        // Rysujemy linie łączące punkty kontrolne
        if (selectedPoints.size() > 1) {
            g2d.setColor(Color.RED);
            Point prev = selectedPoints.get(0);
            for (int i = 1; i < selectedPoints.size(); i++) {
                Point current = selectedPoints.get(i);
                g2d.drawLine(prev.x, prev.y, current.x, current.y);
                prev = current;
            }
        }

        // Rysujemy punkty kontrolne
        g2d.setColor(Color.RED);
        for (Point p : selectedPoints) {
            g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
        }

        // Rysujemy krzywą Béziera jeśli jest włączona
        if (drawCurve && selectedPoints.size() >= 2) {
            g2d.setColor(Color.BLUE);
            bezierDrawer.drawBezier(g2d, getPoints(), parseIntField(curveStepsFiield), 0, 0);
        }
        g2d.dispose();
        return result;
    }


    public List<Point> getPoints() {
        return selectedPoints;
    }

    public void setSelectedPoints(List<Point> selectedPoints) {
        this.selectedPoints = selectedPoints;
    }

    private static class PointListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Point p = (Point) value;
            setText(String.format("P%d (%d, %d)", index, p.x, p.y));
            return this;
        }
    }
}
