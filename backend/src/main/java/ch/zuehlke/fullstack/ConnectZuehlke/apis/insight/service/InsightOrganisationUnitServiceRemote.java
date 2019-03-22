package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.OrganisationUnitDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.GroupParticipant;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Location;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.OrganisationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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
    @Cacheable("organisationUnits")
    public List<OrganisationUnit> getTeams() {
        List<OrganisationUnit> units = getOrganisationUnits();
        return units.stream()
                .filter(OrganisationUnit::isTeam)
                .collect(toList());
    }

    @Override
    @Cacheable("organisationUnits")
    public List<OrganisationUnit> getFocusGroups() {
        List<OrganisationUnit> list = getOrganisationUnits();
        return list.stream()
                .filter(OrganisationUnit::isFocusGroup)
                .collect(toList());
    }

    @Override
    @Cacheable("organisationUnits")
    public List<OrganisationUnit> getTopicTeams() {
        List<OrganisationUnit> list = getOrganisationUnits();
        return list.stream()
                .filter(OrganisationUnit::isTopicTeam)
                .collect(toList());
    }

    @Override
    @Cacheable("organisationUnits")
    public List<OrganisationUnit> getOrganisationStructures() {
        List<OrganisationUnit> list = getOrganisationUnits();
        return list.stream()
                .filter(OrganisationUnit::isFocusGroup)
                .collect(toList());
    }

    @Cacheable("organisationUnitsParticipants")
    @Override
    public List<Employee> getParticipantsInOrganisationUnit(String organisationUnitId, List<Location> locations) {
        ResponseEntity<List<GroupParticipant>> response = this.insightRestTemplate
                .exchange(String.format("/organisationunits/%s/participants", organisationUnitId), GET, null, new ParameterizedTypeReference<List<GroupParticipant>>() {
                });

        return response.getBody()
                .stream()
                .filter(Objects::nonNull)
                .map(GroupParticipant::getEmployee)
                .filter(this::hasNames)
                .filter(this::isEngineer)
                .filter(employee -> fromLocation(employee.getLocation(), locations))
                .map(this::create)
                .collect(toList());

    }

    boolean fromLocation(String employeeLocation, List<Location> locations) {
        if (locations == null) {
            return false;
        }
        if (locations.isEmpty()) {
            return true;
        }

        if (StringUtils.isEmpty(employeeLocation)) {
            return false;
        }

        return locations.stream()
                .map(Location::getCityName)
                .map(String::toLowerCase)
                .anyMatch(l -> employeeLocation.toLowerCase().contains(l));
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
                employeeDto.getCode(),
                employeeDto.getId(),
                employeeDto.getLocation(),
                null,
                null,
                employeeDto.getTitle(),
                employeeDto.getCode());
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
