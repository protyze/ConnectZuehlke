package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProjectDto {
    @JsonProperty("Code")
    private String code;

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDto that = (ProjectDto) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
