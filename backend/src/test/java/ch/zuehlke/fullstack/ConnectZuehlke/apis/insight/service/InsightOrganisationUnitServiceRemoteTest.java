package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Location;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class InsightOrganisationUnitServiceRemoteTest {

    @Test
    public void fromLocationTest() {
        InsightOrganisationUnitServiceRemote orgUnit = new InsightOrganisationUnitServiceRemote(mock(RestTemplate.class));
        boolean actual = orgUnit.fromLocation("Schlieren", Arrays.asList(Location.Sofia, Location.Schlieren, Location.Singapore));
        assertTrue(actual);

        actual = orgUnit.fromLocation("schlieren", Arrays.asList(Location.Sofia, Location.Schlieren, Location.Singapore));
        assertTrue(actual);

        actual = orgUnit.fromLocation("new Belgrade", Arrays.asList(Location.Sofia, Location.Schlieren, Location.New_Belgrade));
        assertTrue(actual);

        actual = orgUnit.fromLocation("sofia", Arrays.asList(Location.Sofia, Location.Schlieren, Location.New_Belgrade));
        assertTrue(actual);
    }
}