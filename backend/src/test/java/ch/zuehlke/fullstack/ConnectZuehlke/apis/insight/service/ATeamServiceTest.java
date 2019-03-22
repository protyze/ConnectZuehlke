package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ATeamServiceTest {

    @Test
    public void calculateATeams() {
        InsightOrganisationUnitService orgServiceMock = mock(InsightOrganisationUnitService.class);
        InsightEmployeeService employeeServiceMock = mock(InsightEmployeeService.class);

        when(orgServiceMock.getFocusGroups()).thenReturn(Arrays.asList(
                new OrganisationUnit("1", "focus group 1", 1, null),
                new OrganisationUnit("2", "focus group 2", 1, null)
        ));

        when(orgServiceMock.getTeams()).thenReturn(Arrays.asList(
                new OrganisationUnit("3", "team 1", 2, null),
                new OrganisationUnit("4", "team 2", 2, null)
        ));


        Employee e1 = new Employee(1, "e1f", "e1l");
        Employee e2 = new Employee(2, "e2f", "e2l");
        Employee e3 = new Employee(3, "e3f", "e3l");
        when(orgServiceMock.getParticipantsInOrganisationUnit("1", new ArrayList<>())).thenReturn(Arrays.asList(
                e1,
                e2,
                e3
                )
        );

        when(orgServiceMock.getParticipantsInOrganisationUnit("2", new ArrayList<>())).thenReturn(Arrays.asList(
                e1,
                e2
                )
        );


        when(orgServiceMock.getParticipantsInOrganisationUnit("3", new ArrayList<>())).thenReturn(Arrays.asList(
                e1,
                e2
                )
        );


        Employee e4 = new Employee(4, "e4f", "e4l");
        Employee e5 = new Employee(5, "e5f", "e5l");
        when(orgServiceMock.getParticipantsInOrganisationUnit("4", new ArrayList<>())).thenReturn(Arrays.asList(e5, e4));

        ATeamService aTeamService = new ATeamService(orgServiceMock, employeeServiceMock);

        List<ATeam> actualATeams = aTeamService.calculateATeams(3, null);

        assertThat(actualATeams).hasSize(3);
        ATeam aTeam = actualATeams.get(0);
        assertThat(aTeam.getTeamMembers()).containsOnly(aTeamMember(e1), aTeamMember(e2), aTeamMember(e3));

    }

    @Test
    public void calculateATeamsOrganisationUnitsTest() {
        InsightOrganisationUnitService orgServiceMock = mock(InsightOrganisationUnitService.class);
        InsightEmployeeService employeeServiceMock = mock(InsightEmployeeService.class);

        when(orgServiceMock.getFocusGroups()).thenReturn(Arrays.asList(
                new OrganisationUnit("1", "focus group 1", 3, null),
                new OrganisationUnit("2", "focus group 2", 3, null)
        ));

        Employee e1 = new Employee(1, "e1f", "e1l");
        Employee e2 = new Employee(2, "e2f", "e2l");
        Employee e3 = new Employee(3, "e3f", "e3l");
        Employee e4 = new Employee(4, "e4f", "e4l");

        when(orgServiceMock.getParticipantsInOrganisationUnit("1", new ArrayList<>())).thenReturn(Arrays.asList(e1, e2));
        when(orgServiceMock.getParticipantsInOrganisationUnit("2", new ArrayList<>())).thenReturn(Arrays.asList(e3, e4));

        ATeamService aTeamService = new ATeamService(orgServiceMock, employeeServiceMock);

        Map<ATeamPair, Double> map = aTeamService.calculateScore(orgServiceMock.getFocusGroups(), new ArrayList<>());

        assertEquals(2, map.size());
        assertEquals(1.0, map.get(new ATeamPair(e1, e2)).doubleValue(), 0.0001);
        assertEquals(1.0, map.get(new ATeamPair(e3, e4)).doubleValue(), 0.0001);
    }

    @Test
    public void calculateTeam() {

        ATeamService aTeamService = new ATeamService(null, null);

        HashMap<ATeamPair, Double> allScores = new HashMap<>();
        Employee e1 = employee(1);
        Employee e2 = employee(2);
        Employee e3 = employee(3);
        Employee e4 = employee(4);
        Employee e5 = employee(5);
        Employee e6 = employee(6);
        Employee e7 = employee(7);
        allScores.put(new ATeamPair(e1, e2), 3.0);
        allScores.put(new ATeamPair(e1, e3), 2.0);
        allScores.put(new ATeamPair(e1, e4), 1.0);
        allScores.put(new ATeamPair(e2, e5), 2.0);
        allScores.put(new ATeamPair(e2, e6), 1.0);
        allScores.put(new ATeamPair(e2, e7), 1.0);
        ATeam aTeam = aTeamService.calculateTeam(7, allScores);
        assertEquals(7, aTeam.getTeamMembers().size());


    }

    private Employee employee(int id) {
        return new Employee(id, "e" + id, "e" + id);
    }

    @Test
    public void calculateATeamsOrganisationUnitsTest1() {
        InsightOrganisationUnitService orgServiceMock = mock(InsightOrganisationUnitService.class);
        InsightEmployeeService employeeServiceMock = mock(InsightEmployeeService.class);

        when(orgServiceMock.getFocusGroups()).thenReturn(Arrays.asList(
                new OrganisationUnit("1", "focus group 1", 3, null),
                new OrganisationUnit("2", "focus group 2", 3, null)
        ));

        Employee e1 = new Employee(1, "e1f", "e1l");
        Employee e2 = new Employee(2, "e2f", "e2l");
        Employee e3 = new Employee(3, "e3f", "e3l");
        Employee e4 = new Employee(4, "e4f", "e4l");

        when(orgServiceMock.getParticipantsInOrganisationUnit("1", new ArrayList<>())).thenReturn(Arrays.asList(e1, e2, e3));
        when(orgServiceMock.getParticipantsInOrganisationUnit("2", new ArrayList<>())).thenReturn(Arrays.asList(e1, e3, e4));

        ATeamService aTeamService = new ATeamService(orgServiceMock, employeeServiceMock);

//        Map<ATeamPair, Double> map = aTeamService.calculateTeam(4, );

//        assertEquals(5, map.size());
//        assertEquals(2.0, map.get(new ATeamPair(e1, e3)).doubleValue(), 0.0001);
//        assertEquals(1.0, map.get(new ATeamPair(e2, e3)).doubleValue(), 0.0001);
//        assertEquals(1.0, map.get(new ATeamPair(e1, e2)).doubleValue(), 0.0001);
//        assertEquals(1.0, map.get(new ATeamPair(e1, e4)).doubleValue(), 0.0001);
//        assertNull(map.get(new ATeamPair(e2, e4)));
    }

    private ATeamMember aTeamMember(Employee e1) {
        return new ATeamMember(e1);
    }

    @Test
    public void combineValueTest() {

        Employee e1 = new Employee(1, "e1f", "e1l");
        Employee e2 = new Employee(2, "e2f", "e2l");
        Employee e3 = new Employee(3, "e3f", "e3l");
        Employee e4 = new Employee(4, "e4f", "e4l");
        ATeamService aTeamService = new ATeamService(null, null);
        HashMap<ATeamPair, Double> first = new HashMap<>();
        HashMap<ATeamPair, Double> second = new HashMap<>();
        first.put(new ATeamPair(e1, e2), 1.0);
        first.put(new ATeamPair(e1, e3), 1.0);
        second.put(new ATeamPair(e1, e4), 1.0);
        second.put(new ATeamPair(e3, e1), 1.0);
        Map<ATeamPair, Double> allScores = aTeamService.combineValues(first, second);
        assertEquals(3, allScores.size());
        assertEquals(2.0, allScores.get(new ATeamPair(e1, e3)).doubleValue(), 0.0001);
        assertNull(allScores.get(new ATeamPair(e2, e1)));
        assertEquals(1.0, allScores.get(new ATeamPair(e1, e2)).doubleValue(), 0.0001);
        assertNull(allScores.get(new ATeamPair(e3, e1)));
        assertEquals(1.0, allScores.get(new ATeamPair(e1, e4)).doubleValue(), 0.0001);
    }
}