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
    public List<ATeam> loadATeams(@RequestParam int nrOfTeamMembers, @RequestParam("location") String... locations) {

        List<Location> filteredLocation = toLocationEnums(locations);
        return aTeamService.calculateATeams(nrOfTeamMembers, filteredLocation);
    }

    private List<Location> toLocationEnums(@RequestParam("location") String[] locations) {
        List<Location> filteredLocation = new ArrayList<>();
        for (String locationString : locations) {
            Optional<Location> locationOptional = Arrays.stream(Location.values())
                    .filter(l -> locationString.equalsIgnoreCase(l.getCityName().toLowerCase()))
                    .findFirst();
            locationOptional.ifPresent(filteredLocation::add);
        }
        return filteredLocation;
    }


}
