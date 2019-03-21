package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.RelationshipData;

import java.io.IOException;
import java.util.List;

public interface InsightEmployeeService {

  List<Employee> getEmployees();

  byte[] getEmployeePicture(int id) throws IOException;

  Employee getEmployee(String code);

  double getWorkedWith(String code1, String code2);

  RelationshipData getRelationshipData();
}
