package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.OrganisationUnitDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.GroupParticipant;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Location;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.OrganisationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.GET;

@Service
@Profile({"prod", "staging"})
public class InsightOrganisationUnitServiceRemote implements InsightOrganisationUnitService {
    private final RestTemplate insightRestTemplate;

    @Autowired
    public InsightOrganisationUnitServiceRemote(RestTemplate insightRestTemplate) {
        this.insightRestTemplate = insightRestTemplate;
    }

    @Override
    public List<OrganisationUnit> getTeams() {
        List<OrganisationUnit> units = getOrganisationUnits();
        return units.stream()
                .filter(OrganisationUnit::isTeam)
                .collect(toList());
    }

    @Override
    public List<OrganisationUnit> getFocusGroups() {
        List<OrganisationUnit> list = getOrganisationUnits();
        return list.stream()
                .filter(OrganisationUnit::isFocusGroup)
                .collect(toList());
    }

    @Override
    public List<OrganisationUnit> getTopicTeams() {
        List<OrganisationUnit> list = getOrganisationUnits();
        return list.stream()
                .filter(OrganisationUnit::isTopicTeam)
                .collect(toList());
    }

    @Override
    public List<OrganisationUnit> getOrganisationStructures() {
        List<OrganisationUnit> list = getOrganisationUnits();
        return list.stream()
                .filter(OrganisationUnit::isFocusGroup)
                .collect(toList());
    }

    @Override
    public List<Employee> getParticipantsInOrganisationUnit(String organisationUnitId, Location... locations) {
        ResponseEntity<List<GroupParticipant>> response = this.insightRestTemplate
                .exchange(String.format("/organisationunits/%s/participants", organisationUnitId), GET, null, new ParameterizedTypeReference<List<GroupParticipant>>() {
                });

        return response.getBody()
                .stream()
                .filter(Objects::nonNull)
                .map(GroupParticipant::getEmployee)
                .filter(this::hasNames)
                .filter(this::isEngineer)
                .map(this::create)
                .collect(toList());

    }

    private boolean fromLocation(String location, Location... locations) {
        if (locations == null) {
            return false;
        }

        if (location == null) {
            return false;
        }

        return Arrays.stream(locations)
                .map(Location::getCityName)
                .map(String::toLowerCase)
                .anyMatch(l -> l.contains(location));
    }

    private boolean isEngineer(EmployeeDto employee) {
        if (employee.getTitle() == null) {
            return false;
        }

        return employee.getTitle().toLowerCase().contains("engineer")
                || employee.getTitle().toLowerCase().contains("architect");
    }

    private boolean hasNames(EmployeeDto employeeDto) {
        return employeeDto.getFirstName() != null && employeeDto.getLastName() != null;
    }

    private Employee create(EmployeeDto employeeDto) {
        return new Employee(employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getId(),
                employeeDto.getLocation(),
                null,
                null,
                employeeDto.getTitle());
    }

    private List<OrganisationUnit> getOrganisationUnits() {
        ResponseEntity<List<OrganisationUnitDto>> response = this.insightRestTemplate
                .exchange("/organisationunits", GET, null, new ParameterizedTypeReference<List<OrganisationUnitDto>>() {
                });

        return response.getBody().stream()
                .flatMap(flatChildren())
                .map(OrganisationUnitDto::toOrganisationUnit)
                .collect(toList());
    }

    private Function<OrganisationUnitDto, Stream<? extends OrganisationUnitDto>> flatChildren() {
        return organisationUnitDto -> Stream.concat(Stream.of(organisationUnitDto), organisationUnitDto.getChildren().stream().flatMap(flatChildren()));
    }
}
