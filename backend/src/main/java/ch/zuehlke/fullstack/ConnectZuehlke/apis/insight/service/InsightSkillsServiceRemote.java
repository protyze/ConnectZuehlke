package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.CapabilityDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Capability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;

@Service
@Profile({"prod", "staging"})
public class InsightSkillsServiceRemote implements InsightSkillsService {
    private final RestTemplate insightRestTemplate;

    @Autowired
    public InsightSkillsServiceRemote(RestTemplate insightRestTemplate) {
        this.insightRestTemplate = insightRestTemplate;
    }

    public List<Capability> getSkills() {
        ResponseEntity<List<CapabilityDto>> response = this.insightRestTemplate
                .exchange("/capabilities", GET, null, new ParameterizedTypeReference<List<CapabilityDto>>() {
                });

        return response.getBody().stream()
                .map(CapabilityDto::toCapability)
                .collect(Collectors.toList());
    }
}
