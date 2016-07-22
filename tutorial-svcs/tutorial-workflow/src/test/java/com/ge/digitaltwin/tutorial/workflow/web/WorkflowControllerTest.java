package com.ge.digitaltwin.tutorial.workflow.web;

import com.ge.digitaltwin.tutorial.workflow.service.WorkflowService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class WorkflowControllerTest {

    private static final String AUTHORIZATION_VARIABLE = "Authorization";
    private WorkflowController workflowController;

    @Mock
    private WorkflowService workflowService;

    @Before
    public void setup() {
        initMocks(this);
        workflowController = new WorkflowController(workflowService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void setsAuthorizationParamIfAvailable() {
        final String expectedAuthorization = "bar";
        workflowController.runWorkflow("foo", expectedAuthorization, null);

        final ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        then(workflowService).should().runWorkflow(anyString(), mapArgumentCaptor.capture());
        assertThat(mapArgumentCaptor.getValue().get(AUTHORIZATION_VARIABLE), is(equalTo(expectedAuthorization)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void doesNotSetAuthorizationParamIfNotAvailable() {
        workflowController.runWorkflow("foo", null, null);

        final ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        then(workflowService).should().runWorkflow(anyString(), mapArgumentCaptor.capture());
        assertFalse(mapArgumentCaptor.getValue().containsKey(AUTHORIZATION_VARIABLE));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void startsWorkflowWithTemplateParameters() {
        final String workflowKey = "fooWithParameters";
        final Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("param1", "val1");
        parameterMap.put("param2", "val2");
        workflowController.runWorkflow(workflowKey, null, parameterMap);

        final ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        then(workflowService).should().runWorkflow(anyString(), mapArgumentCaptor.capture());
        assertFalse(mapArgumentCaptor.getValue().containsKey(AUTHORIZATION_VARIABLE));
        assertThat(mapArgumentCaptor.getValue().get("param1"), is(equalTo("val1")));
        assertThat(mapArgumentCaptor.getValue().get("param2"), is(equalTo("val2")));

    }

    @Test
    @SuppressWarnings("unchecked")
    public void startsWorkflowWithTemplateParametersAndAuthorization() {
        final String workflowKey = "fooWithParameters";
        final String expectedAuthorization = "myAuth";
        final Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("param1", "val1");
        parameterMap.put("param2", "val2");

        final Map<String, Object> expectedMap = new HashMap<>(parameterMap);
        expectedMap.put("authorization", expectedAuthorization);
        workflowController.runWorkflow(workflowKey, expectedAuthorization, parameterMap);
        final ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        then(workflowService).should().runWorkflow(anyString(), mapArgumentCaptor.capture());
        assertThat(mapArgumentCaptor.getValue().get(AUTHORIZATION_VARIABLE), is(equalTo(expectedAuthorization)));
        assertThat(mapArgumentCaptor.getValue().get("param1"), is(equalTo("val1")));
        assertThat(mapArgumentCaptor.getValue().get("param2"), is(equalTo("val2")));
    }

}
