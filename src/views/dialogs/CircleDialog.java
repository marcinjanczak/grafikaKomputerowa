package views.dialogs;

import models.CircleModel;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa okna dialogowego do wprowadzania parametrów koła.
 *
 * <p>Umożliwia użytkownikowi podanie współrzędnych środka, promienia oraz wybór koloru koła.
 * Po zatwierdzeniu danych generowany jest obiekt {@link CircleModel}, w którym przechowywane są informację
 * wykorzystywane do rysowania koła na obrazie.</p>
 *
 * @see CircleModel
 */
public class CircleDialog extends JDialog {

    // Pole dla współrzędnej X środka koła (domyślnie przyjmuje wartość 100).
    private final JTextField xField = new JTextField("100");

    // Pole dla współrzędnej Y środka koła (domyślnie przyjmuje wartość 100).
    private final JTextField yField = new JTextField("100");

    // Pole tekstowe dla promienia koła (domyślnie przyjmuje wartość 50).
    private final JTextField radiusField = new JTextField("50");

    // Aktualnie wybrany kolor koła (domyślnie niebieski).
    private Color selectedColor = Color.BLUE;

    // Flaga informująca, czy użytkownik zatwierdził dane, przez przycisk OK.
    private Boolean confirmed = false;

    public CircleDialog(JFrame parent) {
        super(parent, "Podaj parametry", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getActionPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Tworzy główny panel zawierający pola do wprowadzania danych oraz przycisk wyboru koloru.
     *
     * @return Panel z polami tekstowymi i przyciskiem do wyboru koloru.
     */
    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Środek X:"));
        panel.add(xField);
        panel.add(new JLabel("Środek Y:"));
        panel.add(yField);
        panel.add(new JLabel("Promień:"));
        panel.add(radiusField);

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

    /**
     * Tworzy panel z przyciskami akcji: "OK" i "Anuluj".
     *
     * @return Panel z przyciskami potwierdzenia i anulowania.
     */
    private JPanel getActionPanel() {
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

    /**
     * Zwraca obiekt {@link CircleModel} z wprowadzonymi danymi. Obiekt jest zwracany tylko, gdy użytkownik zatwierdził okno przez przycisk OK.
     *
     * @return Obiekt CircleModel z danymi koła lub {@code null}, jeśli użytkownik anulował formularz.
     */
    public CircleModel getCircle() {
        if (confirmed) {
            return new CircleModel(
                    parseField(xField),
                    parseField(yField),
                    parseField(radiusField),
                    selectedColor
            );
        }
        return null;
    }

    /**
     * Sprawdza, czy wszystkie pola zawierają poprawne wartości numeryczne.
     *
     * @return {@code true}, jeśli dane są poprawne; {@code false} w przeciwnym razie.
     */
    private Boolean validateFields() {
        return parseField(xField) != null &&
                parseField(yField) != null &&
                parseField(radiusField) != null;
    }

    /**
     * Konwertuje dane pobrane z pola tekstowego {@link JTextField} na liczbę całkowitą.
     *
     * @param field Pole tekstowe do sparsowania.
     * @return Liczba całkowita lub {@code null}, jeśli nie udało się sparsować wartości.
     */
    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}