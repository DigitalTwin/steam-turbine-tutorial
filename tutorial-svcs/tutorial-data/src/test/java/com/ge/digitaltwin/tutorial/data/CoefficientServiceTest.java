package com.ge.digitaltwin.tutorial.data;

import com.ge.digitaltwin.tutorial.common.coefficient.ModelCoefficient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class CoefficientServiceTest {

    private static final URI EXPECTED_COEFFICIENT_SERVICE_URI = URI.create("http://www.foo.com");
    private CoefficientService coefficientService;
    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void setup() {
        final CoefficientProperties coefficientProperties = new CoefficientProperties();
        coefficientProperties.setCoefficientService(EXPECTED_COEFFICIENT_SERVICE_URI);

        final RestTemplate restTemplate = new RestTemplate();
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);

        coefficientService = new CoefficientService(coefficientProperties, restTemplate);
    }

    @Test
    public void fetchesCoefficientsForAssetId() {
        mockRestServiceServer.expect(requestTo(EXPECTED_COEFFICIENT_SERVICE_URI + "?assetId=foo")).andExpect(method(GET))
                .andRespond(withSuccess("{\n" +
                        "  \"assetId\" : \"1\",\n" +
                        "  \"m\" : 4.0,\n" +
                        "  \"b\" : 2.0,\n" +
                        "  \"_links\" : {\n" +
                        "    \"self\" : {\n" +
                        "      \"href\" : \"http://localhost:8080/persistence/modelCoefficients/dd9a1544-9e67-4b79-8369-339fdbc72f79\"\n" +
                        "    },\n" +
                        "    \"modelCoefficient\" : {\n" +
                        "      \"href\" : \"http://localhost:8080/persistence/modelCoefficients/dd9a1544-9e67-4b79-8369-339fdbc72f79\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}", APPLICATION_JSON));

        final ModelCoefficient modelCoefficient = coefficientService.fetchCoefficientForAssetId("foo");
        assertThat(modelCoefficient.getM(), is(4d));
        assertThat(modelCoefficient.getB(), is(2d));
    }
}
