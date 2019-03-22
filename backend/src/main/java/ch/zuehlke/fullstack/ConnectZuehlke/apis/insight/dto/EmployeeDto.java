package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.JobProfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties
public class EmployeeDto {
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("JobProfile")
    private JobProfileDto jobProfileDto;
    @JsonProperty("EntryDate")
    private LocalDateTime entryDate;
    @JsonProperty("Title")
    private String title;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public JobProfileDto getJobProfileDto() {
        return jobProfileDto;
    }

    private String getCode() {
        return code;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public Employee toEmployee() {
        return new Employee(
                getFirstName(),
                getLastName(),
                getId(),
                getLocation(),
                getJobProfileDto() == null? new JobProfile("") : getJobProfileDto().toJobProfile(),
                getEntryDate().toLocalDate(),
                getTitle());
    }

}
