package com.ge.digitaltwin.tutorial.simulator;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpMethod;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WorkflowService.class, OAuth2RestTemplate.class})
public class WorkflowServiceTest {

    private static final URI WORKFLOW_URI = URI.create("http://www.foo.com");
    private static final HttpMethod WORKFLOW_METHOD = POST;
    private static final String EXPECTED_HEADER = "Predix-Zone-Id";
    private static final String EXPECTED_HEADER_VALUE = "934txs";
    private WorkflowService workflowService;

    @Mock
    private ClientCredentialsResourceDetails clientCredentialsResourceDetails;

    @Mock
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        mockStatic(IOUtils.class);
        whenNew(OAuth2RestTemplate.class).withArguments(same(clientCredentialsResourceDetails)).thenReturn(oAuth2RestTemplate);

        final WorkflowProperties workflowProperties = new WorkflowProperties();
        workflowProperties.setWorkflowUri(WORKFLOW_URI);
        workflowProperties.setWorkflowMethod(WORKFLOW_METHOD);
        workflowProperties.setWorkflowHeaders(new ArrayList<>());
        workflowProperties.getWorkflowHeaders().add(new WorkflowHeader(EXPECTED_HEADER, EXPECTED_HEADER_VALUE));

        workflowService = new WorkflowService(workflowProperties, clientCredentialsResourceDetails);
    }

    @Test
    public void invokesWorkflow() {
        workflowService.invokeWorkflow("asset456", 124342000L, 124663000L);
        then(oAuth2RestTemplate).should().execute(eq(WORKFLOW_URI), eq(WORKFLOW_METHOD), any(), any());
    }

    @Test
    public void extractsDataFromResponse() {
        final String expectedData = "foo";
        assertThat(invokeMethod(workflowService, "extractData", new MockClientHttpResponse(expectedData.getBytes(), OK)), is(equalTo(expectedData)));
    }

    @Test
    public void createsRequestWithHeaders() {
        final String assetId = "assetxyz";
        final String bodyTag = "bodyParamTag";
        final long startTime = 124342000L;
        final long endTime = 124663000L;
        final String expectedBodyString = "{\"" + bodyTag + "\":{\"assetId\":\"" + assetId + "\",\"startTime\":" + startTime + ",\"endTime\":" + endTime + "}}";

        final MockClientHttpRequest clientHttpRequest = new MockClientHttpRequest();
        invokeMethod(workflowService, "createRequest", clientHttpRequest, bodyTag, assetId, startTime, endTime);
        assertThat(clientHttpRequest.getHeaders().getFirst(EXPECTED_HEADER), is(equalTo(EXPECTED_HEADER_VALUE)));
        assertThat(clientHttpRequest.getBodyAsString(), is(equalTo(expectedBodyString)));
    }

}
