package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class FocusGroupParticipant   {
    @JsonProperty("Employee")
    private EmployeeDto employee;

    public FocusGroupParticipant(EmployeeDto employee) {
        this.employee = employee;
    }

    public FocusGroupParticipant() {
    }

    public EmployeeDto getEmployee() {
        return employee;
    }
}
