package models;

import java.awt.*;

/**
 * Klasa reprezentująca model koła.
 *
 * <p>Przechowuje informacje o położeniu, wymiarach oraz kolorze koła.</p>
 *
 * <ul>
 *     <li>{@code centerX} – współrzędna X środka koła.</li>
 *     <li>{@code startY} – współrzędna Y środka koła.</li>
 *     <li>{@code radius} – promień.</li>
 *     <li>{@code color} – kolor wypełnienia.</li>
 * </ul>
 */
public class CircleModel {

    private final Integer centerX;

    private final Integer centerY;

    private final Integer radius;

    private final Color color;

    public CircleModel(Integer centerX, Integer centerY, Integer radius, Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.color = color;
    }

    public Integer getCenterX() {
        return centerX;
    }

    public Integer getCenterY() {
        return centerY;
    }

    public Integer getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }
}