package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.Objects;

public class Score implements Comparable<Score> {
    private Double value=0.0;

    public Score(Double value) {
        this.value = value;
    }

    public Score() {
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return Double.compare(score.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Score o) {
        return -value.compareTo(o.value);
    }
}
