package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.EmployeeProject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class EmployeeProjectDto {
    @JsonProperty("Project")
    private ProjectDto projectDto;
    @JsonProperty("FromEffective")
    private LocalDateTime fromEffective;
    @JsonProperty("ToEffective")
    private LocalDateTime toEffective;

    public ProjectDto getProjectDto() {
        return projectDto;
    }

    public LocalDateTime getFromEffective() {
        return fromEffective;
    }

    public LocalDateTime getToEffective() {
        return toEffective;
    }

    public EmployeeProject toEmployeeProject() {
        return new EmployeeProject(
                getProjectDto().getCode(),
                getFromEffective().toLocalDate(),
                getToEffective().toLocalDate()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeProjectDto that = (EmployeeProjectDto) o;
        return Objects.equals(projectDto, that.projectDto) &&
                Objects.equals(fromEffective, that.fromEffective) &&
                Objects.equals(toEffective, that.toEffective);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectDto, fromEffective, toEffective);
    }
}
