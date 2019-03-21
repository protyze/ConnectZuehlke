package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class ZuehlkeTeam {
    private String teamName;

    public ZuehlkeTeam(String teamName) {
        this.teamName = teamName;
    }

    public ZuehlkeTeam() {
    }

    public String getTeamName() {
        return teamName;
    }
}
