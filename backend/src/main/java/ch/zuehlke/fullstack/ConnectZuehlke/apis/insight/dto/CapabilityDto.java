package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Capability;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class CapabilityDto {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Capability toCapability() {
        return new Capability(
                getId(),
                getName()
        );
    }
}
