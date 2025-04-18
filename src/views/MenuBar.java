package views;

import javax.swing.*;

/**
 * Pasek menu aplikacji, zawierający opcje zarządzania plikami, edycji oraz operacji na panelach.
 * Klasa rozszerza {@link JMenuBar} i definiuje strukturę menu dla aplikacji.
 */
public class MenuBar extends JMenuBar {

    private final JMenu fileMenu;
    private final JMenu leftPanelMenu;
    private final JMenu rightPanelMenu;
    private final JMenu editPanelMenu;
    private final  JMenu transformationsPanelMenu;
    private final JMenu dilatationsPanelMenu;

    private final JMenuItem openFileMenuItem;
    private final JMenuItem saveFileMenuItem;
    private final JMenuItem exitMenuItem;

    private final JMenuItem clearLeftPanelMenuItem;
    private final JMenuItem copyLeftPanelMenuItem;

    private final JMenuItem clearRightPanelMenuItem;
    private final JMenuItem copyRightPanelMenuItem;

    private final JMenuItem drawCircleMenuItem;
    private final JMenuItem drawRectangleMenuItem;
    private final JMenuItem drawLineMenuItem;

    private final JMenuItem changeContrastAndBrightness;
    private final JMenuItem transformNegative;
    private final JMenuItem transformNormalizeBrightness;

    private final JMenuItem makegrByAyaverageGray;
    private final JMenuItem makeByRedGray;
    private final JMenuItem makeByGreenGray;
    private final JMenuItem makeByBlueGrey;
    private final JMenuItem makeByYUV;

    public MenuBar() {
        // Tworzenie głównych menu
        fileMenu = new JMenu("Plik");
        leftPanelMenu = new JMenu("Lewy panel");
        rightPanelMenu = new JMenu("Prawy panel");
        editPanelMenu = new JMenu("Edycja");
        transformationsPanelMenu = new JMenu("Transformacje");
        dilatationsPanelMenu = new JMenu("Szarosć");

        // Menu plik
        openFileMenuItem = new JMenuItem("Otwórz");
        saveFileMenuItem = new JMenuItem("Zapisz");
        exitMenuItem = new JMenuItem("Zakończ");


        // Menu Panel lewy
        clearLeftPanelMenuItem = new JMenuItem("Wyczyść");
        copyLeftPanelMenuItem = new JMenuItem("Kopiuj obraz");

        // Menu Panel prawy
        clearRightPanelMenuItem = new JMenuItem("Wyczyść");
        copyRightPanelMenuItem = new JMenuItem("Kopiuj");

        // Menu Edycja
        drawCircleMenuItem = new JMenuItem("Narysuj koło");
        drawRectangleMenuItem = new JMenuItem("Narysuj prostokąt");
        drawLineMenuItem = new JMenuItem("Narysuj linie");

        // Menu Transformacje


        changeContrastAndBrightness = new JMenuItem("Zmień kontrast i jasność");
        transformNegative = new JMenuItem("Zrób Negatyw");
        transformNormalizeBrightness = new JMenuItem("Normalizuj jasność");

        makegrByAyaverageGray = new JMenuItem("Poszarz według sredniej");
        makeByRedGray = new JMenuItem("Według czerwonego");
        makeByGreenGray = new JMenuItem("Według zielonego");
        makeByBlueGrey = new JMenuItem("Według niebieskiego");
        makeByYUV = new JMenuItem("Według YUV");

        // Dodanie elementów do menu Plik
        fileMenu.add(openFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitMenuItem);

        // Dodanie elementów do menu Panel lewy
        leftPanelMenu.add(clearLeftPanelMenuItem);
        leftPanelMenu.add(copyLeftPanelMenuItem);

        // Dodanie elementów do menu Panel prawy
        rightPanelMenu.add(clearRightPanelMenuItem);
        rightPanelMenu.add(copyRightPanelMenuItem);

        // Dodanie elementów do menu Edycja
        editPanelMenu.add(drawCircleMenuItem);
        editPanelMenu.add(drawRectangleMenuItem);
        editPanelMenu.add(drawLineMenuItem);

        // Dodawnie elementów do Transformacja
        transformationsPanelMenu.add(makegrByAyaverageGray);
        transformationsPanelMenu.add(changeContrastAndBrightness);
        transformationsPanelMenu.add(transformNegative);
        transformationsPanelMenu.add(transformNormalizeBrightness);

        // Dodawanie elementów Szaroci;
        dilatationsPanelMenu.add(makegrByAyaverageGray);
        dilatationsPanelMenu.add(makeByRedGray);
        dilatationsPanelMenu.add(makeByGreenGray);
        dilatationsPanelMenu.add(makeByBlueGrey);
        dilatationsPanelMenu.add(makeByYUV);

        // Dodawanie wszystkich menu do paska menu
        add(fileMenu);
        add(leftPanelMenu);
        add(rightPanelMenu);
        add(editPanelMenu);
        add(transformationsPanelMenu);
        add(dilatationsPanelMenu);
    }

    public JMenuItem getOpenFileMenuItem() {
        return openFileMenuItem;
    }

    public JMenuItem getSaveFileMenuItem() {
        return saveFileMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JMenuItem getClearLeftPanelMenuItem() {
        return clearLeftPanelMenuItem;
    }

    public JMenuItem getCopyLeftPanelMenuItem() {
        return copyLeftPanelMenuItem;
    }

    public JMenuItem getClearRightPanelMenuItem() {
        return clearRightPanelMenuItem;
    }

    public JMenuItem getDrawCircleMenuItem() {
        return drawCircleMenuItem;
    }

    // TODO: Dodać metody getter dla nowych elementów menu.
    public JMenuItem getDrawRectangleMenuItem(){
        return drawRectangleMenuItem;
    }
    public JMenuItem getCopyRightPanelMenuItem(){
        return copyRightPanelMenuItem;
    }
    public  JMenuItem getDrawLineMenuItem(){
        return drawLineMenuItem;
    }
    public JMenuItem getMakegrByAyaverageGray(){
        return makegrByAyaverageGray;
    }
    public JMenuItem getChangeContrastAndBrightness(){
        return changeContrastAndBrightness;
    }
    public JMenuItem getTransformNegative(){
        return transformNegative;
    }
    public JMenuItem getTransformNormalizeBrightness(){
        return transformNormalizeBrightness;
    }

    public JMenuItem getMakeByRedGray() {
        return makeByRedGray;
    }

    public JMenuItem getMakeByGreenGray() {
        return makeByGreenGray;
    }

    public JMenuItem getMakeByBlueGrey() {
        return makeByBlueGrey;
    }

    public JMenuItem getMakeByYUV() {
        return makeByYUV;
    }
}
