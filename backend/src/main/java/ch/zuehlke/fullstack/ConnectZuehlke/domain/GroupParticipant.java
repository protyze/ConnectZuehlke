package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class GroupParticipant {
    @JsonProperty("Employee")
    private EmployeeDto employee;

    public GroupParticipant(EmployeeDto employee) {
        this.employee = employee;
    }

    public GroupParticipant() {
    }

    public EmployeeDto getEmployee() {
        return employee;
    }
}
