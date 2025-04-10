package views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MakeCurveDialog extends JDialog{
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
