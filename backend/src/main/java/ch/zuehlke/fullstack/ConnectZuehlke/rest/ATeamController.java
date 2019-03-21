package ch.zuehlke.fullstack.ConnectZuehlke.rest;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightOrganisationUnitService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
public class ATeamController {

    private final InsightOrganisationUnitService organisationUnitService;

    public ATeamController(InsightOrganisationUnitService organisationUnitService) {
        this.organisationUnitService = organisationUnitService;
    }

    @GetMapping("/api/ateams")
    public ResponseEntity<List<ATeam>> loadATeams() {

        ArrayList<ATeamMember> aTeamsByFocusGroup = loadATeamsByFocusGroup();
        ArrayList<ATeamMember> aTeamsByZuehlkeTeam = loadATeamsByZuehlkeTeam();

        ArrayList<ATeamMember> aTeams = new ArrayList<>();
        aTeams.addAll(aTeamsByFocusGroup);
        aTeams.addAll(aTeamsByZuehlkeTeam);
        ATeam aTeam = new ATeam(aTeams, new Score(1.0));
        return ResponseEntity.ok(Arrays.asList(aTeam));
    }

    private ArrayList<ATeamMember> loadATeamsByZuehlkeTeam() {
        ArrayList<ATeamMember> zuehlkeTeamMembers = new ArrayList<>();

        List<OrganisationUnit> zuehlkeTeams = organisationUnitService.getTeams();

        for (OrganisationUnit team : zuehlkeTeams) {
            List<ATeamMember> participantsOfZuehlkeTeam = organisationUnitService.getParticipantsInOrganisationUnit(team.getId()).stream()
                    .map(e -> new ATeamMember(new Employee(e.getId(), e.getFirstName(), e.getLastName()), false, null, null, null, new ZuehlkeTeam(team.getName()), null))
                    .collect(toList());
            zuehlkeTeamMembers.addAll(participantsOfZuehlkeTeam);
        }

        return zuehlkeTeamMembers;
    }

    private ArrayList<ATeamMember> loadATeamsByFocusGroup() {

        List<OrganisationUnit> focusGroups = organisationUnitService.getFocusGroups();
        ArrayList<ATeamMember> teamMembers = new ArrayList<>();

        for (OrganisationUnit focusGroup : focusGroups) {
            List<ATeamMember> participantsInFocusGroup = organisationUnitService.getParticipantsInOrganisationUnit(focusGroup.getId()).stream()
                    .map(e -> new ATeamMember(new Employee(e.getId(), e.getFirstName(), e.getLastName()), false, null, null, null, null, new FocusGroup(focusGroup.getName())))
                    .collect(toList());
            teamMembers.addAll(participantsInFocusGroup);
        }
        return teamMembers;
    }

}
