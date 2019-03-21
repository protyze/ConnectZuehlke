package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.Objects;

public class ATeamPair  {
    private final Employee e1;
    private final Employee e2;

    public ATeamPair(Employee e1, Employee e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public Employee getE1() {
        return e1;
    }

    public Employee getE2() {
        return e2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATeamPair aTeamPair = (ATeamPair) o;
        return Objects.equals(e1, aTeamPair.e1) &&
                Objects.equals(e2, aTeamPair.e2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }
}
