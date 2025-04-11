package views.dialogs;

import models.ContrAndBrightModel;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ChangleContrastAndBrightenssDialog extends JDialog {
    private final JSlider brightnessSlider = new JSlider(JSlider.HORIZONTAL, 0,255,0);
    private final JSlider contrastSlider = new JSlider(JSlider.HORIZONTAL,0,100,10);
    private Boolean confirmed = false;

    public ChangleContrastAndBrightenssDialog(JFrame parent) {
        super(parent,"Podaj parametry:", true);
        setSize(400,200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel,BorderLayout.CENTER);

        JPanel buttonPanel = getButtonpanel();
        add(buttonPanel,BorderLayout.SOUTH);

    }
    private JPanel getMainPanel(){

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Podaj wartość kontrastu "));
        panel.add(getContrastSlider());
        panel.add(new JLabel("Podaj wartość jasności"));
        panel.add(getBrightnessSlider());

        return panel;
    }
    private JSlider getBrightnessSlider(){
        brightnessSlider.setMajorTickSpacing(50);
        brightnessSlider.setPaintLabels(true);

        return brightnessSlider;
    }
    private JSlider getContrastSlider(){
        contrastSlider.setMajorTickSpacing(10);
        contrastSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(1, new JLabel("0.1"));
        labels.put(10, new JLabel("1.0"));
        labels.put(20, new JLabel("2.0"));
        labels.put(30, new JLabel("3.0"));
        contrastSlider.setLabelTable(labels);

        return contrastSlider;
    }
    private JPanel getButtonpanel(){
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Anuluj");

        // Obsługa przycisku OK. Sprawdzenie poprawności danych i zamknięcie okna. Jeśli dane są niepoprawne, wyświetlany jest komunikat z błędem.
        okButton.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Obsługa przycisku Anuluj przez zamknięcie okna dialogowego.
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }
    public ContrAndBrightModel getContrAndBrightModel() {
        if (confirmed) {
            int brightnessValue  = brightnessSlider.getValue();
            double contrastValue = contrastSlider.getValue() / 10.0;

            return new ContrAndBrightModel(contrastValue,brightnessValue);
        }
        return null;
    }
   private Boolean validateFields() {
        return true;
    }

    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private Double parseFieldDouble(JTextField field){
        try {
            return Double.parseDouble(field.getText());
        }catch (NumberFormatException e){
            return null;
        }
    }
}
