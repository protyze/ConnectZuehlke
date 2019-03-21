package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectDto {
    @JsonProperty("Code")
    private String code;

    public String getCode() {
        return code;
    }
}
