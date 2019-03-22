package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.List;

public class ATeam implements Comparable<ATeam>{
    private List<ATeamMember> teamMembers;
    private Score score;

    public ATeam(List<ATeamMember> teamMembers, Score score) {
        this.teamMembers = teamMembers;
        this.score = score;
    }

    public ATeam() {

    }

    public List<ATeamMember> getTeamMembers() {
        return teamMembers;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public int compareTo(ATeam o) {
        return score.compareTo(o.score);
    }
}
