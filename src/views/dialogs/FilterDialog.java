package views.dialogs;

import models.FilterModel;

import javax.sql.rowset.serial.SerialStruct;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class FilterDialog extends JDialog{
    private JComboBox<String> filterComboBox;
    private JButton applyButton;
    private JButton cancelButton;
    private Boolean confirmed = false;

    public FilterDialog(JFrame parent) {
        super(parent,"Podaj parametry:", true);
        setSize(400,300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(20,20));

        JPanel panel = getMainPanel();
        add(panel,BorderLayout.CENTER);
        JPanel buttonPanel = getButtonPanel();
        add(buttonPanel,BorderLayout.SOUTH);

    }
    private JPanel getMainPanel(){
        var panel = new JPanel(new GridLayout(2,0,10,10));

        panel.add(filterComboBox);
        panel.add(new JLabel("Dodaj zakres (0-255)"));
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
    private JComboBox<String> getFilterComboBox() throws IOException {
        var comboBox = new JComboBox<String>();

        /// TODO: zaimplementować dodanie nazw i tablic do tego czaru.

        ArrayList<FilterModel> modelArrayList = (ArrayList<FilterModel>) FilterModel.readFiltersFromFile("macierze.txt");

        for(FilterModel fm : modelArrayList){
            comboBox.addItem(fm.getName());
        }
        return comboBox;
    }

}
