package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.ArrayList;
import java.util.List;

public class RelationshipData {

    List<EmployeeNode> employeeNodes = new ArrayList();
    List<EmployeeRelationship> employeeRelations = new ArrayList();

    public RelationshipData(List<EmployeeNode> employeeNodes, List<EmployeeRelationship> employeeRelations) {
        this.employeeNodes = employeeNodes;
        this.employeeRelations = employeeRelations;
    }

    public List<EmployeeNode> getEmployeeNodes() {
        return employeeNodes;
    }

    public List<EmployeeRelationship> getEmployeeRelations() {
        return employeeRelations;
    }
}
