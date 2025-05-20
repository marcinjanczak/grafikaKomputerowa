package views.dialogs;

import models.LineModel;

import javax.swing.*;
import java.awt.*;

public class LinesDialog extends JDialog {

    private final JTextField intervalsField = new JTextField("10");
    private Color selectedColor = Color.BLUE;
    private Boolean confirmed = false;

    public LinesDialog(JFrame parent) {
        super(parent, "Podaj parametry:", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getButtonpanel();
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private JPanel getMainPanel() {

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Podaj odstęp między liniami: "));
        panel.add(intervalsField);

        // Wybór koloru
        JButton colorButton = new JButton("Wybierz kolor");
        colorButton.setBackground(selectedColor);

        /**
         *  Ustawnie nasłuchu na przycisk Wybierz kolor.
         *  Po kliknięciu otwiera okno dialogowe wyboru koloru ({@link JColorChooser}).
         */
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Wybierz kolor", selectedColor);
            if (newColor != null) {
                selectedColor = newColor;
                colorButton.setBackground(selectedColor);
            }
        });

        panel.add(new JLabel("Kolor:"));
        panel.add(colorButton);

        return panel;
    }

    private JPanel getButtonpanel() {
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


    public LineModel getLine() {
        if (confirmed) {
            return new LineModel(
                    parseField(intervalsField),
                    selectedColor
            );
        }
        return null;
    }

    private Boolean validateFields() {
        return parseField(intervalsField) != null;
    }

    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
