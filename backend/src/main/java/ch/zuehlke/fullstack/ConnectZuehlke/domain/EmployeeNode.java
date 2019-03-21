package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class EmployeeNode {

    int key;
    String text;
    String color;

    public EmployeeNode(int key, String text, String color) {
        this.key = key;
        this.text = text;
        this.color = color;
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
