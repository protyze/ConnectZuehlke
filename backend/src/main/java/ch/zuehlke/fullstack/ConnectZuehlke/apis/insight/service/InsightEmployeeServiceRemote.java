package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.EmployeeNode;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.EmployeeRelationship;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.RelationshipData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public RelationshipData getRelationshipData() {
        List<EmployeeNode> nodeList = new ArrayList<>();
        nodeList.add(new EmployeeNode(0, "Joshua ", "#b1a0e7"));
        nodeList.add(new EmployeeNode(1, "Daniel ", "#b1a0e7"));
        nodeList.add(new EmployeeNode(2, "Robert ", "#b1a0e7"));
        nodeList.add(new EmployeeNode(3, "Noah ", "#b1a0e7"));
        nodeList.add(new EmployeeNode(4, "Anthony", "#b1a0e7"));
        List<EmployeeRelationship>employeeRelations = new ArrayList<>();
        employeeRelations.add(new EmployeeRelationship(0, 5, "#b1a0e7"));
        employeeRelations.add(new EmployeeRelationship(2, 3, "#b1a0e7"));
        employeeRelations.add(new EmployeeRelationship(1, 2, "#b1a0e7"));
        employeeRelations.add(new EmployeeRelationship(2, 4, "#b1a0e7"));
        employeeRelations.add(new EmployeeRelationship(1, 35, "#b1a0e7"));
        return new RelationshipData(nodeList, employeeRelations);
    }

    @Override
    public Employee getEmployee(String code) {
        ResponseEntity<EmployeeDto> response = this.insightRestTemplate
                .getForEntity("/employees/" + code, EmployeeDto.class);

        return response.getBody().toEmployee();
    }
}
