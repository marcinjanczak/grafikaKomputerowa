package views;

import controllers.FileController;
import controllers.ImageController;
import models.*;
import views.dialogs.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {

    // Domyślne wymiary okna aplikacji
    private final static Integer DEFAULT_WIDTH = 800;
    private final static Integer DEFAULT_HEIGHT = 600;

    // Panele do wyświetlania obrazów
    private final ImagePanel leftPanel;
    private final ImagePanel rightPanel;

    // Pasek menu aplikacji
    private final MenuBar menuBar;

    // Kontrolery obsługujące obrazy oraz pliki
    private final ImageController imageController;
    private final FileController fileController;

    public MainFrame() {
        super("Grafika komputerowa");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicjalizacja komponentów
        leftPanel = new ImagePanel("Obraz wczytany");
        rightPanel = new ImagePanel("Obraz zmodyfikowany");
        menuBar = new MenuBar();

        // Inicjalizacja kontrolerów
        imageController = new ImageController(this);
        fileController = new FileController(this);

        // Utworzenie kontenera do organizacji komponentów interfejsu użytkownika, ustawiamy siatkę 1x2 dla 2 paneli z obrazami
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        add(contentPanel, BorderLayout.CENTER);


        setJMenuBar(menuBar); // Dodanie menu do okna
        setMenuBarListeners(); // Ustawienie nasłuchu na zdarzenia wywołania opcji z menubar

        setLocationRelativeTo(null); // Centrowanie okna na ekranie
        setVisible(true); // Ustawienie widoczności okna
    }

    public ImagePanel getRightPanel() {
        return rightPanel;
    }

    public ImagePanel getLeftPanel() {
        return leftPanel;
    }

    public void adjustWindowSize() {
        var image = leftPanel.getModel().getImage();
        if (image == null) {
            return;
        }
        // Obliczenie nowej szerokości okna.
        // Okno powinno mieć co najmniej dwukrotność szerokości obrazu.
        // + 100 - dodaje dodatkową przestrzeń dla marginesów.
        int newWidth = Math.max(getWidth(), image.getWidth() * 2 + 100);

        // Obliczenie nowej wysokości okna.
        // + 100 - dodaje dodatkową przestrzeń dla marginesów.
        int newHeight = Math.max(getHeight(), image.getHeight() + 100);
        setSize(newWidth, newHeight);
        setLocationRelativeTo(null);
    }

    private void setMenuBarListeners() {
        menuBar.getOpenFileMenuItem().addActionListener(e -> showFileChooserDialog());

        menuBar.getSaveFileMenuItem().addActionListener(e -> showSaveFileDialog());
        menuBar.getExitMenuItem().addActionListener(e -> System.exit(0));

        menuBar.getCopyLeftPanelMenuItem().addActionListener(e -> imageController.copyLeftPanel());
        menuBar.getClearLeftPanelMenuItem().addActionListener(e -> imageController.clearLeftPanel());

        menuBar.getClearRightPanelMenuItem().addActionListener(e -> imageController.clearRightPanel());
        menuBar.getCopyRightPanelMenuItem().addActionListener(e -> imageController.copyRightPanel());

        menuBar.getDrawCircleMenuItem().addActionListener(e -> showCircleDialog());
        menuBar.getDrawRectangleMenuItem().addActionListener(e -> showRectangleDialog());
        menuBar.getDrawLineMenuItem().addActionListener(e -> showLineDalog());

        menuBar.getMakegrByAyaverageGray().addActionListener(e -> transformDilataionPicture());
        menuBar.getChangeContrastAndBrightness().addActionListener(e -> changeContrastAndBrightess());
        menuBar.getTransformNegative().addActionListener(e -> transformNegative());
        menuBar.getTransformNormalizeBrightness().addActionListener(e -> transformNormalizeBrightness());

        menuBar.getMakeByRedGray().addActionListener(e -> makeGrayByRed());
        menuBar.getMakeByGreenGray().addActionListener(e -> makeGrayByGreen());
        menuBar.getMakeByBlueGrey().addActionListener(e -> makeGrayByBlue());
        menuBar.getMakeByYUV().addActionListener(e -> makeByYUV());

        menuBar.getSplotFilter().addActionListener(e -> {
            try {
                showSplotFilterDialog();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        menuBar.getStatisticFilter().addActionListener(e -> showStatisticFilterDialog());
        menuBar.getGradientFilter().addActionListener(e -> showGradientFilterDialog());

        menuBar.getMakeCurve().addActionListener(e-> makeCurve());

    }

    private void showRectangleDialog() {
        RectangleDialog dialog = new RectangleDialog(this);
        dialog.setVisible(true);
        RectangleModel rectangle = dialog.getRectangle();
        if(rectangle != null){
            imageController.drawRectangle(rectangle);
        }
    }

    private void showSplotFilterDialog() throws IOException {
        SplotFilterDialog dialog = new SplotFilterDialog(this);
        dialog.setVisible(true);
        SplitFilterModel model = dialog.getSplitFilter();
        System.out.println(model.getMartix());

//        imageController.
//        SplitFilterModel filterModel = dialog.getFilterModel();
//        System.out.println(filterModel.getName()+" value: "+filterModel.getThershold() );
//        imageController.addFilter(filterModel);

       // TODO : Pobrać dane z okna dialogowego i przesłać do imageController
    }
    private void showStatisticFilterDialog(){
        StatisticFilterDialog dialog = new StatisticFilterDialog(this);
        dialog.setVisible(true);

    }
    private void showGradientFilterDialog(){
        GradientFilterDialog dialog = new GradientFilterDialog(this);
        dialog.setVisible(true);

    }




    private void transformDilataionPicture(){
        imageController.transformDilataion();
    }
    private void makeCurve(){
        ImagePanel.MakeCurveDialog dialog = new ImagePanel.MakeCurveDialog(this);
        dialog.setVisible(true);

    }

    private void changeContrastAndBrightess(){
        ChangleContrastAndBrightenssDialog dialog = new ChangleContrastAndBrightenssDialog(this);
        dialog.setVisible(true);
        ContrAndBrightModel contrAndBrightModel = dialog.getContrAndBrightModel();

       imageController.changeContrastAndBrightness(contrAndBrightModel);
    }
    private void showCircleDialog() {
        CircleDialog dialog = new CircleDialog(this);
        dialog.setVisible(true);
        CircleModel circle = dialog.getCircle();

        if (circle != null) {
            imageController.drawCircle(circle);
        }
    }
    private void showLineDalog(){
        LinesDialog dialog = new LinesDialog(this);
        dialog.setVisible(true);
        LineModel line = dialog.getLine();

        if(line != null){
            imageController.drawline(line);
        }
}
    private void transformNegative(){
        imageController.transformNegative();
    }
    private void transformNormalizeBrightness(){
        imageController.normalizeBrightness();
    }
    private void makeGrayByRed(){
        imageController.makeGrayByRed();
    }
    private void makeGrayByGreen(){
        imageController.makeGrayByGreen();
    }
    private void makeGrayByBlue(){
        imageController.makeGrayByBlue();
    }
    private void makeByYUV(){
        imageController.makeGrayByYUV();
    }


    private void showFileChooserDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wybierz plik graficzny");
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            imageController.loadImage(file);
        }
    }

    private void showSaveFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz obraz");
        fileChooser.setFileFilter(new FileNameExtensionFilter("BMP & PNG Images", "bmp", "png"));
        int returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileController.saveFile(file);
        }
    }

}