package views;

import models.FilterModel;

import javax.swing.*;
import java.awt.*;

public class FilterDialog extends JDialog{
    private JComboBox<String> filterComboBox;
    private JButton applyButton;
    private JButton cancelButton;
    private JTextField theresholdField;
    private Boolean confirmed = false;

    public FilterDialog(JFrame parent) {
        super(parent,"Podaj parametry:", true);
        setSize(400,300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(20,20));

        JPanel panel = getMainPanel();
        add(panel,BorderLayout.CENTER);
//
        JPanel buttonPanel = getButtonPanel();
        add(buttonPanel,BorderLayout.SOUTH);

    }
    private JPanel getMainPanel(){
        var panel = new JPanel(new GridLayout(4,0,10,10));
        String[] filters = {
                "blur", "gaussian Blur",
                "sharpen", "laplace",
                "medianowy", "maksymalny", "minimalny",
                "sobel (Poziomy)", "sobel (Pionowy)", "roberts"
        };
        filterComboBox = new JComboBox<>(filters);
        theresholdField = new JTextField("128");


        panel.add(filterComboBox);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Dodaj zakres (0-255)"));
        panel.add(theresholdField);
        return panel;
    }
    private JPanel getButtonPanel(){
        var panel = new JPanel(new GridLayout(0,2,10,10));
        applyButton = new JButton("Zastosuj");
        cancelButton = new JButton("Anuluj");

        applyButton.addActionListener(e -> {
            if(validateFields()){
                confirmed = true;
                dispose();
            }else {
                JOptionPane.showMessageDialog(this,"Brak wpisanych danych","Bład",JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelButton.addActionListener(e -> dispose());

        panel.add(applyButton);
        panel.add(cancelButton);
        return panel;
    }
    public FilterModel getFilterModel(){
        if(confirmed){
            return new FilterModel(getSelectedComboBoxText(filterComboBox),
                    parseField(theresholdField));
        }
        return null;
    }
    public static String getSelectedComboBoxText(JComboBox<String> comboBox) {
        if (comboBox == null) {
            throw new IllegalArgumentException("ComboBox nie może być null");
        }

        Object selected = comboBox.getSelectedItem();
        return (selected != null) ? selected.toString() : null;
    }
    private Boolean validateFields() {
        return parseField(theresholdField) != null;
    }
    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
