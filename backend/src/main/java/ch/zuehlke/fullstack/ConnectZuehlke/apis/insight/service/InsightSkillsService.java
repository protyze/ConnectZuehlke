package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Capability;

import java.util.List;

public interface InsightSkillsService {

    List<Capability> getSkills();
}
