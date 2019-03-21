package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.OrganisationUnit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InsightOrganisationUnitServiceMock implements InsightOrganisationUnitService {
    @Override
    public List<OrganisationUnit> getTeams() {
        return Arrays.asList(new OrganisationUnit("1", "Test", 2, Collections.emptyList()));
    }

    @Override
    public List<OrganisationUnit> getFocusGroups() {
        return Arrays.asList(new OrganisationUnit("1", "Test", 3, Collections.emptyList()));
    }

    @Override
    public List<OrganisationUnit> getTopicTeams() {
        return Arrays.asList(new OrganisationUnit("1", "Test", 5, Collections.emptyList()));
    }

    @Override
    public List<OrganisationUnit> getOrganisationStructures() {
        return Arrays.asList(new OrganisationUnit("1", "Test", 1, Collections.emptyList()));
    }

    @Override
    public List<Employee> getParticipantsInOrganisationUnit(String organisationUnitId) {

        return null;
    }
}
