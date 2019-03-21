package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class EmployeeRelationship {

    int from;
    int to;
    String color;

    public EmployeeRelationship(int from, int to, String color) {
        this.from = from;
        this.to = to;
        this.color = color;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getColor() {
        return color;
    }
}
