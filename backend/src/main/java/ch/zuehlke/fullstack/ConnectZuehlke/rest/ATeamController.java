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
        List<OrganisationUnit> focusGroups = organisationUnitService.getFocusGroups();
        List<ATeam> focusGroupToEmployees = new ArrayList<>();

        for (OrganisationUnit focusGroup : focusGroups) {
            List<ATeamMember> participantsInFocusGroup = organisationUnitService.getParticipantsInFocusGroup(focusGroup.getId()).stream()
                    .map(this::create)
                    .collect(toList());

            Score score = new Score(1.0);
            ATeam aTeam = new ATeam(participantsInFocusGroup, score);
            focusGroupToEmployees.add(aTeam);

        }

        Collections.sort(focusGroupToEmployees);
        return ResponseEntity.ok(focusGroupToEmployees);
    }

    private ATeamMember create(Employee e) {
        return new ATeamMember(e.getId(), e.getFirstName(), e.getLastName(), false, null, null, null, null);
    }
}
