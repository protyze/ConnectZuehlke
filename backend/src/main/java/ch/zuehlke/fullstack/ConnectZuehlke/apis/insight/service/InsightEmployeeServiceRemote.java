package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeProjectDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.GET;

@Service
@Profile({"prod", "staging"})
public class InsightEmployeeServiceRemote implements InsightEmployeeService {

    private final RestTemplate insightRestTemplate;


    @Autowired
    public InsightEmployeeServiceRemote(RestTemplate insightRestTemplate) {
        this.insightRestTemplate = insightRestTemplate;
    }

    @Override
    public List<Employee> getEmployees() {
        ResponseEntity<List<EmployeeDto>> response = this.insightRestTemplate
                .exchange("/employees", GET, null, new ParameterizedTypeReference<List<EmployeeDto>>() {
                });

        return response.getBody().stream()
                .map(EmployeeDto::toEmployee)
                .collect(toList());
    }

    @Override
    public byte[] getEmployeePicture(int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = this.insightRestTemplate
                .exchange("/employees/" + id + "/picture?large=false", GET, entity, byte[].class, "1");
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        throw new IllegalStateException("Status code was not 200");
    }

    @Override
    public RelationshipData getRelationshipData(String codes) {
        if (codes != null) {
            // create EmployeeNode
            List<EmployeeNode> employeeNodes = new ArrayList<>();
            List<EmployeeRelationship> relationships = new ArrayList<>();

            String[] split = codes.split(",");
            for (int i = 0; i < split.length; i++) {
                employeeNodes.add(new EmployeeNode(i, split[i].toLowerCase(), "#b1a0e7"));
            }
            // get employees to create ATeamPairs
            List<Employee> employees = new ArrayList<>();
            for (EmployeeNode node : employeeNodes) {
                try {
                    employees.add(getEmployee(node.getText()));
                } catch (Exception e) {
                    // do nothing...
                }
            }
            // create all pairs
            List<ATeamPair> aTeamPairs = new ArrayList<>();
            for (Employee e1 : employees) {
                for (Employee e2 : employees) {
                    aTeamPairs.add(new ATeamPair(e1, e2));
                }
            }
            // get scores for Pairs
            for (ATeamPair currentPair : aTeamPairs) {
                Double score = ATeamService.getScores(currentPair);
                EmployeeNode from = getNodeFromList(currentPair.getE1().getCode(), employeeNodes);
                EmployeeNode to = getNodeFromList(currentPair.getE2().getCode(), employeeNodes);
                if (to != null && score != null && from != null) {
                    relationships.add(
                            new EmployeeRelationship(
                                    from.getKey(),
                                    to.getKey(),
                                    EmployeeRelationship.getColorForScore(score))
                    );
                }
            }
            return new RelationshipData(employeeNodes, relationships);
        }
        return null;
    }

    EmployeeNode getNodeFromList(String code, List<EmployeeNode> nodes) {
        for (EmployeeNode node : nodes) {
            if (node.getText().toLowerCase().equals(code.toLowerCase())) {
                return node;
            }
        }
        return null;
    }

    @Override
    @Cacheable("employee")
    public Employee getEmployee(String code) {
        ResponseEntity<EmployeeDto> response = null;
        try {
            response = this.insightRestTemplate
                    .getForEntity("/employees/" + code, EmployeeDto.class);

        } catch (Exception e) {
            System.out.println("fuck");
            return null;
        }

        return response.getBody().toEmployee();
    }

    @Override
    public double getWorkedWith(String code1, String code2) {
        ResponseEntity<List<EmployeeProjectDto>> response1 = getEmployeeProjects(code1);
        ResponseEntity<List<EmployeeProjectDto>> response2 = getEmployeeProjects(code2);

        Employee employee1 = getEmployee(code1);
        Employee employee2 = getEmployee(code2);
        if (employee1 == null || employee2 == null) {
            return 0.0;
        }

        List<EmployeeProject> projects1 = getCustomerProjects(response1);
        List<EmployeeProject> projects2 = getCustomerProjects(response2);

        int daysTogether = 0;

        for (EmployeeProject project1 : projects1) {
            Optional<EmployeeProject> optionalEmployeeProject = projects2.stream().filter(employeeProject -> employeeProject.getCode().equals(project1.getCode())).findFirst();
            if (optionalEmployeeProject.isPresent()) {
                EmployeeProject project2 = optionalEmployeeProject.get();

                LocalDate start1 = project1.getFromEffective();
                LocalDate start2 = project2.getFromEffective();
                LocalDate end1 = project1.getToEffective();
                LocalDate end2 = project2.getToEffective();

                long interval1 = DAYS.between(start1, end1);
                long interval2 = start1.isBefore(end2) ? DAYS.between(start1, end2) : 0;
                long interval3 = start2.isBefore(end1) ? DAYS.between(start2, end1) : 0;
                long interval4 = DAYS.between(start2, end2);

                long daysTogetherOnProject = Math.min(interval1, interval2);
                daysTogetherOnProject = Math.min(daysTogetherOnProject, interval3);
                daysTogetherOnProject = Math.min(daysTogetherOnProject, interval4);
                daysTogether += daysTogetherOnProject;

            }
        }

        long days1 = DAYS.between(employee1.getEntryDate(), LocalDate.now());
        long days2 = DAYS.between(employee2.getEntryDate(), LocalDate.now());

        return ((double) daysTogether) / (0.5 * (days1 + days2));
    }

    @Cacheable("project")
    public ResponseEntity<List<EmployeeProjectDto>> getEmployeeProjects(String code1) {
        return this.insightRestTemplate
                .exchange("/employees/" + code1 + "/projects/history", GET, null, new ParameterizedTypeReference<List<EmployeeProjectDto>>() {
                });
    }

    private List<EmployeeProject> getCustomerProjects(ResponseEntity<List<EmployeeProjectDto>> response1) {
        return response1.getBody().stream()
                .filter(employeeProjectDto -> employeeProjectDto.getToEffective() != null)
                .filter(employeeProjectDto -> employeeProjectDto.getProjectDto().getCode() != null)
                .filter(employeeProjectDto -> employeeProjectDto.getProjectDto().getCode().startsWith("C"))
                .map(EmployeeProjectDto::toEmployeeProject).collect(Collectors.toList());
    }
}
