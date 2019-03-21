package ch.zuehlke.fullstack.ConnectZuehlke.rest;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightSkillsService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Capability;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkillsRestController {
    private final InsightSkillsService insightSkillsService;

    public SkillsRestController(InsightSkillsService insightSkillsService) {
        this.insightSkillsService = insightSkillsService;
    }

    @GetMapping("/api/capabilities")
    public List<Capability> getSkills() {
        return insightSkillsService.getSkills();
    }
}
