package ch.zuehlke.fullstack.ConnectZuehlke.rest;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.ATeamService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ATeamController {


    private ATeamService aTeamService;

    @Autowired
    public ATeamController(ATeamService aTeamService) {
        this.aTeamService = aTeamService;
    }

    @GetMapping("/api/ateams")
    public List<ATeam> loadATeams(@RequestParam int nrOfTeamMembers) {
        return aTeamService.calculateATeams(nrOfTeamMembers);
    }


}
