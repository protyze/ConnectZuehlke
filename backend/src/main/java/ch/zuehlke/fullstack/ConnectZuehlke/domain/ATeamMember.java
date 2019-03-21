package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.List;

public class ATeamMember {
    private int id;
    private String firstName;
    private String lastName;
    private boolean isAvailable;
    private Title grade;
    private String unit;
    private List<Skill> skills;
    private List<FocusGroup> focusGroups;

    public ATeamMember(int id, String firstName, String lastName,boolean isAvailable, Title grade, String unit, List<Skill> skills, List<FocusGroup> focusGroups) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAvailable = isAvailable;
        this.grade = grade;
        this.unit = unit;
        this.skills = skills;
        this.focusGroups = focusGroups;
    }

    public ATeamMember() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public List<FocusGroup> getFocusGroups() {
        return focusGroups;
    }
}
