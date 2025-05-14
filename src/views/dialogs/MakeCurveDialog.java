package views.dialogs;

import views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MakeCurveDialog extends JDialog {
    private List<Point> selectedPoints = new ArrayList<>();
    private BufferedImage image;
    private JList<Point> pointsList;
    private DefaultListModel<Point> listModel;

    public MakeCurveDialog(JFrame parent) {
        super(parent, "Wybierz punkty", true);
        setSize(1000, 800);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        listModel = new DefaultListModel<>();
        pointsList = new JList<>(listModel);

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

        add(imagePanel, gbc);

        gbc.gridx = 9;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // Zajmuje 1/4 szerokości
        gbc.gridheight = 9; // Zajmuje 3/4 wysokości
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
    }

    private JPanel getMainPanel(JFrame parent) {
        this.image = ((MainFrame) parent).getLeftPanel().getModel().getImage();

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
                        g.fillOval(p.x - 5, p.y - 5, 10, 10);
                    }
                    if(selectedPoints.size() > 1){
                        g.setColor(Color.RED);
                        Point prev = selectedPoints.get(0);
                        for (int i = 1; i < selectedPoints.size(); i++) {
                            Point current = selectedPoints.get(i);
                            g.drawLine(prev.x, prev.y, current.x, current.y);
                            prev = current ;
                        }
                    }
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("Wczytaj najpierw obraz!", 50, 50);
                }
            }
        };
//            adjustWindowSize(image);

        // Obsługa myszy
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (image != null) {
                    int selectedIndex = pointsList.getSelectedIndex();

                    if(selectedIndex != -1){
                        Point p = selectedPoints.get(selectedIndex);
                        p.setLocation(e.getPoint());
                        listModel.set(selectedIndex, p);
                    }else {
                        selectedPoints.add(e.getPoint());
                        listModel.addElement(e.getPoint());
                    }
//                    selectedPoints.add(e.getPoint());
//                    listModel.addElement(e.getPoint());
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
        int newWidth = Math.max(getWidth(), image.getWidth() + 300);

        int newHeight = Math.max(getHeight(), image.getHeight() + 300);
        setSize(newWidth, newHeight);
        setLocationRelativeTo(null);
    }

    private JPanel getButtonpanel() {
        var panel = new JPanel();

        JButton okButton = new JButton("Narysuj");
        JButton cancelButton = new JButton("Anuluj");

        cancelButton.addActionListener(e -> dispose());
        okButton.addActionListener(e -> getPoints());

        panel.add(okButton);
        panel.add(cancelButton);

        return panel;
    }

    private JPanel getPointsArea() {
        JPanel panel = new JPanel(new BorderLayout());

        pointsList.setCellRenderer(new PointListRenderer());
        JScrollPane scrollPane = new JScrollPane(pointsList);
        panel.add(scrollPane,BorderLayout.CENTER);


        JButton editPointButton = new JButton("Edutuj punkt");
        JButton deletePointButton = new JButton("Usuń punkt");
        var buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(editPointButton);
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
    private JPanel getPointManipulationPanel() {
        var finalpanel = new JPanel(new GridLayout(1, 3));


        var manipulationPanel = new JPanel(new GridLayout(3, 3, 10, 10));
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

        var matrixPanel = new JPanel(new GridLayout(3, 3));

        var curvePanel = new JPanel(new GridLayout(1, 2));


        finalpanel.add(manipulationPanel);
        finalpanel.add(matrixPanel);
        finalpanel.add(curvePanel);

        return finalpanel;
    }

    public List<Point> getPoints() {
        for (Point points : selectedPoints) {
            System.out.println(points);
        }
        return selectedPoints;
    }
}
