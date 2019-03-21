package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.time.LocalDate;

public class EmployeeProject {
    private final String code;
    private final LocalDate fromEffective;
    private final LocalDate toEffective;

    public EmployeeProject(String code, LocalDate fromEffective, LocalDate toEffective) {
        this.code = code;
        this.fromEffective = fromEffective;
        this.toEffective = toEffective;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getFromEffective() {
        return fromEffective;
    }

    public LocalDate getToEffective() {
        return toEffective;
    }
}
