package ch.zuehlke.fullstack.ConnectZuehlke.rest;


import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightEmployeeService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.RelationshipData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class EmployeeRestController {
    private final InsightEmployeeService employeeService;

    public EmployeeRestController(InsightEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/api/employees")
    public List<Employee> employeeList() {
        return employeeService.getEmployees();
    }

    @GetMapping("/api/employee/{code}")
    public Employee employee(@PathVariable(value = "code") String code) {
        return employeeService.getEmployee(code);
    }

    @GetMapping(value = "/api/employee/{id}/picture",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getEmployeePicture(@PathVariable(value = "id") int id) throws IOException {
        return employeeService.getEmployeePicture(id);
    }

    @GetMapping("/api/employee/{code1}/workedWith/{code2}")
    public double workedWith(
            @PathVariable(value = "code1") String code1,
            @PathVariable(value = "code2") String code2) {
        return employeeService.getWorkedWith(code1, code2);
    }

    @GetMapping(value = "/api/employee/relations")
    public @ResponseBody
    RelationshipData getRelationshipData(@RequestParam(value = "employees") String employees) {
        return employeeService.getRelationshipData(employees);
    }

}