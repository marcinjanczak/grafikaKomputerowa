package views.dialogs;

import models.SplitFilterModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SplotFilterDialog extends JDialog{
    private JComboBox<String> filterComboBox;
    private ArrayList<SplitFilterModel> modelArrayList;
    private JButton applyButton;
    private JButton cancelButton;
    private Boolean confirmed = false;

    public SplotFilterDialog(JFrame parent) throws IOException {
        super(parent,"Podaj parametry:", true);
        setSize(300,100);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(20,20));

        JPanel panel = getMainPanel();
        add(panel,BorderLayout.CENTER);
        JPanel buttonPanel = getButtonPanel();
        add(buttonPanel,BorderLayout.SOUTH);
    }
    private JPanel getMainPanel() throws IOException {
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
    private JComboBox<String> getFilterComboBox() throws IOException {
        modelArrayList = (ArrayList<SplitFilterModel>) SplitFilterModel.readFiltersFromFile("macierze.txt");

        filterComboBox = new JComboBox<>(
        modelArrayList.stream()
                .map(SplitFilterModel::getName)
                .toArray(String[]::new)
      );
        return filterComboBox;
    }
    public SplitFilterModel getSplitFilter(){
        if(confirmed){
            String selectedFilter = (String) filterComboBox.getSelectedItem();
            float[][] matrix ;
            matrix = modelArrayList.stream()
                    .filter(modelArrayList -> modelArrayList.getName().equals(selectedFilter))
                    .map(SplitFilterModel::getMartix)
                    .findFirst()
                    .orElse(null);
            return new SplitFilterModel(selectedFilter,matrix);
        }
        return null;
    }
}
