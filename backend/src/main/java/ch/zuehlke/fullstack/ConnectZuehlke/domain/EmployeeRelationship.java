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

    public static String getColorForScore(double score) {
        if (score > 10) {
            score = 10;
        }
        int shift = (int) score * 25;
        int blue = 255 - shift;
        int red = shift;

        return "#" + Integer.toHexString(red) + "00" + Integer.toHexString(blue);
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
