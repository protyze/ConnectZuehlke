package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeProjectDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.EmployeeProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public Employee getEmployee(String code) {
        ResponseEntity<EmployeeDto> response = this.insightRestTemplate
                .getForEntity("/employees/" + code, EmployeeDto.class);

        return response.getBody().toEmployee();
    }

    @Override
    public double getWorkedWith(String code1, String code2) {
        ResponseEntity<List<EmployeeProjectDto>> response1 = this.insightRestTemplate
                .exchange("/employees/" + code1 + "/projects/history", GET, null, new ParameterizedTypeReference<List<EmployeeProjectDto>>() {
                });

        ResponseEntity<List<EmployeeProjectDto>> response2 = this.insightRestTemplate
                .exchange("/employees/" + code2 + "/projects/history", GET, null, new ParameterizedTypeReference<List<EmployeeProjectDto>>() {
                });

        Employee employee1 = getEmployee(code1);
        Employee employee2 = getEmployee(code2);

        List<EmployeeProject> projects1 = getCustomerProjects(response1);
        List<EmployeeProject> projects2 = getCustomerProjects(response2);

        int daysTogether = 0;

        for(EmployeeProject project1 : projects1) {
            Optional<EmployeeProject> optionalEmployeeProject = projects2.stream().filter(employeeProject -> employeeProject.getCode().equals(project1.getCode())).findFirst();
            if(optionalEmployeeProject.isPresent()) {
                EmployeeProject project2 = optionalEmployeeProject.get();

                LocalDate start1 = project1.getFromEffective();
                LocalDate start2 = project2.getFromEffective();
                LocalDate end1 = project1.getToEffective();
                LocalDate end2 = project2.getToEffective();

                long interval1 = DAYS.between(start1, end1);
                long interval2 = start1.isBefore(end2)? DAYS.between(start1, end2): 0;
                long interval3 = start2.isBefore(end1)? DAYS.between(start2, end1): 0;
                long interval4 = DAYS.between(start2, end2);

                long daysTogetherOnProject = Math.min(interval1, interval2);
                daysTogetherOnProject = Math.min(daysTogetherOnProject, interval3);
                daysTogetherOnProject = Math.min(daysTogetherOnProject, interval4);
                daysTogether += daysTogetherOnProject;

            }
        }

        long days1 = DAYS.between(employee1.getEntryDate(), LocalDate.now());
        long days2 = DAYS.between(employee2.getEntryDate(), LocalDate.now());

        return ((double) daysTogether)/(0.5 * (days1 + days2));
    }

    private List<EmployeeProject> getCustomerProjects(ResponseEntity<List<EmployeeProjectDto>> response1) {
        return response1.getBody().stream()
                .filter(employeeProjectDto -> employeeProjectDto.getToEffective() != null)
                .filter(employeeProjectDto -> employeeProjectDto.getProjectDto().getCode() != null)
                .filter(employeeProjectDto -> employeeProjectDto.getProjectDto().getCode().startsWith("C"))
                .map(EmployeeProjectDto::toEmployeeProject).collect(Collectors.toList());
    }
}
