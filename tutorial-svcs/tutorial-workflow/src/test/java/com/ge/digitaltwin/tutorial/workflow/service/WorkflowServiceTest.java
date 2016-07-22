package com.ge.digitaltwin.tutorial.workflow.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;

public class WorkflowServiceTest {

    @Mock
    private RuntimeService runtimeService;

    @Mock
    private ProcessInstance processInstance;

    private WorkflowService workflowService;

    @Before
    public void setup() {
        initMocks(this);
        workflowService = new WorkflowService(runtimeService);
    }

    @Test
    public void startsWorkflow() {
        final String workflowKey = "foo";
        workflowService.runWorkflow(workflowKey, null);
        then(runtimeService).should().startProcessInstanceByKey(eq(workflowKey), anyMapOf(String.class, Object.class));
    }

    @Test
    public void processInstanceDetailsMapContainsNineElements() {
        Map<String, String> processInstanceDetails = invokeMethod(workflowService, "getProcessInstanceInformationDetails", processInstance);
        assertThat(processInstanceDetails, notNullValue());
        assertThat(processInstanceDetails.size(), is(9));
        assertTrue(processInstanceDetails.containsKey("Execution ID"));
        assertTrue(processInstanceDetails.containsKey("Deployment ID"));
        assertTrue(processInstanceDetails.containsKey("Process Instance ID"));
        assertTrue(processInstanceDetails.containsKey("Process Definition ID"));
        assertTrue(processInstanceDetails.containsKey("Process Definition Key"));
        assertTrue(processInstanceDetails.containsKey("Process Definition Name"));
        assertTrue(processInstanceDetails.containsKey("Business Key"));
        assertTrue(processInstanceDetails.containsKey("Name"));
        assertTrue(processInstanceDetails.containsKey("Parent ID"));
    }

    @Test
    public void processInstanceDetailsMapIsEmptyForNullProcessInstance() {
        ProcessInstance nullProcessInstance = null;
        Map<String, String> processInstanceDetails = invokeMethod(workflowService, "getProcessInstanceInformationDetails", nullProcessInstance);
        assertThat(processInstanceDetails, notNullValue());
        assertThat(processInstanceDetails.size(), is(0));
    }
}
