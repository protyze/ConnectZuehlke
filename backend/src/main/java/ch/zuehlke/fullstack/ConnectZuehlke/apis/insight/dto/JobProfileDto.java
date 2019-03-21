package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.JobProfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class JobProfileDto {
    @JsonProperty("Name")
    private String name;

    public String getName() {
        return name;
    }

    public JobProfile toJobProfile() {
        return new JobProfile(getName());
    }
}
