package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.awt.*;
import java.util.Random;

public class EmployeeNode {

    int key;
    String text;
    String color;

    public EmployeeNode(int key, String text, String color) {
        this.key = key;
        this.text = text;
        this.color = color == null ? getRandomPastelColor() : color;
    }

    private String getRandomPastelColor() {
        //to get rainbow, pastel colors
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        Color hsbColor = Color.getHSBColor(hue, saturation, luminance);
        return "#"
                + Integer.toHexString(hsbColor.getRed())
                + Integer.toHexString(hsbColor.getGreen())
                + Integer.toHexString(hsbColor.getBlue());
    }

    public int getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }
}
