package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@Profile({"ci", "default"})
public class InsightEmployeeServiceMocked implements InsightEmployeeService {

    public static final List<Employee> EMPLOYEES = asList(
            new Employee("Klaus", "Mustermann", "klmu", 1, "Test", new JobProfile("TestProfile"), LocalDate.of(1980, 1, 1),"Test Title")
    );

    public List<Employee> getEmployees() {
        return EMPLOYEES;
    }

    @Override
    public byte[] getEmployeePicture(int id) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("demo_picture.jpg");
        return IOUtils.toByteArray(classPathResource.getInputStream());
    }

    @Override
    public Employee getEmployee(String code) {
        return EMPLOYEES.stream().filter(employee -> employee.getCode().equals(code)).findFirst().orElse(null);
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
    public double getWorkedWith(String code1, String code2) {
        return 0.5;
    }
}
