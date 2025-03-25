package models;

import java.awt.*;

/**
 * Klasa reprezentująca model prostokąta.
 *
 * <p>Przechowuje informacje o położeniu, wymiarach oraz kolorze prostokąta.</p>
 *
 * <ul>
 *     <li>{@code startX} – współrzędna X lewego górnego rogu.</li>
 *     <li>{@code startY} – współrzędna Y lewego górnego rogu.</li>
 *     <li>{@code width} – szerokość prostokąta.</li>
 *     <li>{@code height} – wysokość prostokąta.</li>
 *     <li>{@code color} – kolor wypełnienia.</li>
 * </ul>
 */
public class RectangleModel {

    private final Integer startX;

    private final Integer startY;

    private final Integer width;

    private final Integer height;

    private final Color color;

    public RectangleModel() {
        this.startX = 0;
        this.startY = 0;
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
    }

    public RectangleModel(Integer startX, Integer startY, Integer width, Integer height, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * TODO: Należy dodać konstruktor z parametrami, aby można było tworzyć obiekt z określonymi wymiarami i kolorem.
     * public RectangleModel(...
     *      ...
     * }
     */

    public Color getColor() {
        return color;
    }

    public Integer getStartX() {
        return startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
