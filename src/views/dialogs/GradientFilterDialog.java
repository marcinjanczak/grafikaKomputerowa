package views.dialogs;

import models.GradientModel;

import javax.swing.*;
import java.awt.*;

public class GradientFilterDialog extends JDialog {
    private JComboBox<String> gradientComboBox;
    private JTextField thersholdField;
    private JComboBox<String> modeChoseComboBox;


    private JButton applyButton;
    private JButton cancelButton;
    private Boolean confirmed = false;

    public GradientFilterDialog(JFrame parent) {
        super(parent,"Podaj parametry:", true);
        setSize(350,200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(20,20));

        JPanel panel = getMainPanel();
        add(panel,BorderLayout.CENTER);
        JPanel buttonPanel = getButtonPanel();
        add(buttonPanel,BorderLayout.SOUTH);
    }
    private JPanel getMainPanel(){
        var panel = new JPanel(new GridLayout(5,0,10,10));
        gradientComboBox = new JComboBox<>(new String[]{
                "prosty",
                "roberts",
                "progowy"});

        modeChoseComboBox = new JComboBox<>(new String[]{
                "Białe tło, resszta niepretworzona",
                "Krawędzie czarne, tło oryginalne",
                "Czarne krawędzie na czarnym tle"
        });

        thersholdField = new JTextField("100");

        panel.add(gradientComboBox);
        panel.add(new JLabel("Dodaj gradientu (0 - 255 tylko dla progowego)"));
        panel.add(thersholdField);
        panel.add(new JLabel("Dodaj tryb przy progowaniu."));
        panel.add(modeChoseComboBox);
        return panel;
    }
    private JPanel getButtonPanel(){
        var panel = new JPanel(new GridLayout(0,2,10,10));
        applyButton = new JButton("Zastosuj");
        cancelButton = new JButton("Anuluj");

        applyButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());

        panel.add(applyButton);
        panel.add(cancelButton);
        return panel;
    }
    public GradientModel getGradientModel(){
        if(confirmed){
            int value = parseField(thersholdField);
            if(validateValue(value));

            return new GradientModel(
                    (String) gradientComboBox.getSelectedItem(),
                    value,
                    getMode()
            );
        }
        return null;
    }
    private boolean validateValue(Integer value){
        return value < 0 || value > 255;
    }
    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private Integer getMode(){
        Integer mode;
        String modeName = (String) modeChoseComboBox.getSelectedItem();
        switch (modeName){
            case"Białe tło, resszta niepretworzona":
                mode = 1;
                break;
            case"Krawędzie czarne, tło oryginalne":
                mode = 2;
                break;
            case"Czarne krawędzie na czarnym tle":
                mode = 3;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + modeName);
        }
        return mode;
    }

}
