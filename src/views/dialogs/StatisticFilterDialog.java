package views.dialogs;

import models.SplitFilterModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class StatisticFilterDialog extends JDialog {
    private JComboBox<String> filterComboBox;
    private JButton applyButton;
    private JButton cancelButton;
    private Boolean confirmed = false;

    public StatisticFilterDialog(JFrame parent) {
        super(parent,"Podaj parametry:", true);
        setSize(300,100);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(20,20));

        JPanel panel = getMainPanel();
        add(panel,BorderLayout.CENTER);
        JPanel buttonPanel = getButtonPanel();
        add(buttonPanel,BorderLayout.SOUTH);

    }
    private JPanel getMainPanel(){
        var panel = new JPanel(new GridLayout(1,0,10,10));
        filterComboBox = getFilterComboBox();
        panel.add(filterComboBox);
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
    private JComboBox<String> getFilterComboBox(){
         filterComboBox = new JComboBox<>(new String[]{"medianowy","minimalny","maksymalny"});
        return filterComboBox;
    }
    public String getStatisticFilterName(){
        if(confirmed){
            String name = (String) filterComboBox.getSelectedItem();
            return name;
        }
        return null;
    }
}
