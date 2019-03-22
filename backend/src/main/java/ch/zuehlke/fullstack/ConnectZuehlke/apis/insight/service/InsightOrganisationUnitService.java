package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Location;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.OrganisationUnit;

import java.util.List;

public interface InsightOrganisationUnitService {

    List<OrganisationUnit> getTeams();

    List<OrganisationUnit> getFocusGroups();

    List<OrganisationUnit> getTopicTeams();

    List<OrganisationUnit> getOrganisationStructures();

    List<Employee> getParticipantsInOrganisationUnit(String organisationUnitId, Location... locations);
}
