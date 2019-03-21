package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.List;
import java.util.Objects;

public class ATeamMember {
    private final Employee employee;
    private boolean isAvailable;
    private Title grade;
    private String unit;
    private List<Skill> skills;

    private ZuehlkeTeam zuehlkeTeam;

    private FocusGroup focusGroups;

    public ATeamMember(Employee employee, boolean isAvailable, Title grade, String unit, List<Skill> skills, ZuehlkeTeam zuehlkeTeam, FocusGroup focusGroups) {

        this.employee = employee;

        this.isAvailable = isAvailable;
        this.grade = grade;
        this.unit = unit;
        this.skills = skills;
        this.zuehlkeTeam = zuehlkeTeam;
        this.focusGroups = focusGroups;
    }

    public ATeamMember(Employee employee) {

        this.employee = employee;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Title getGrade() {
        return grade;
    }

    public String getUnit() {
        return unit;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public FocusGroup getFocusGroups() {
        return focusGroups;
    }

    public ZuehlkeTeam getZuehlkeTeam() {
        return zuehlkeTeam;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATeamMember that = (ATeamMember) o;
        return Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee);
    }

    @Override
    public String toString() {
        return "ATeamMember{" +
                "employee=" + employee +
                '}';
    }
}
