package com.ge.digitaltwin.tutorial.simulator;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.springframework.http.HttpMethod.POST;

import java.net.URI;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SimulatorService.class, WorkflowService.class})
public class SimulatorServiceTest {

    private SimulatorService service;

    private static final URI WORKFLOW_URI = URI.create("http://www.foo.com");
    private static final HttpMethod WORKFLOW_METHOD = POST;
    private static final String EXPECTED_HEADER = "Predix-Zone-Id";
    private static final String EXPECTED_HEADER_VALUE = "934txs";

    @Mock
    private ClientCredentialsResourceDetails clientCredentialsResourceDetails;

    @Mock 
    private WorkflowService workflowService;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        mockStatic(IOUtils.class);

        final WorkflowProperties workflowProperties = new WorkflowProperties();
        workflowProperties.setWorkflowUri(WORKFLOW_URI);
        workflowProperties.setWorkflowMethod(WORKFLOW_METHOD);
        workflowProperties.setWorkflowHeaders(new ArrayList<>());
        workflowProperties.getWorkflowHeaders().add(new WorkflowHeader(EXPECTED_HEADER, EXPECTED_HEADER_VALUE));

        whenNew(WorkflowService.class).withArguments(same(workflowProperties), same(clientCredentialsResourceDetails)).thenReturn(workflowService);

        service = new SimulatorService(workflowProperties, clientCredentialsResourceDetails);
    }

    @Test
    public void checkTimeParameters() {
        final String assetId = "asset789";
        final Long startTime = 1454243000L;
        final Integer intervalMilliseconds = 60000;
        final Long endTime = startTime + intervalMilliseconds*3;

        service.runSimulation(assetId, startTime, endTime, intervalMilliseconds);
        then(workflowService).should().invokeWorkflow(eq(assetId), eq(startTime), eq(startTime + intervalMilliseconds));
        then(workflowService).should().invokeWorkflow(eq(assetId), eq(startTime + intervalMilliseconds), eq(startTime + intervalMilliseconds*2));
        then(workflowService).should().invokeWorkflow(eq(assetId), eq(startTime + intervalMilliseconds*2), eq(startTime + intervalMilliseconds*3));
    }

}
