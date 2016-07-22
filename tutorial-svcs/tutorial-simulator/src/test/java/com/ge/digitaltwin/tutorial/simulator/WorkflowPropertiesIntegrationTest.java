package com.ge.digitaltwin.tutorial.simulator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimulatorApplication.class)
@WebIntegrationTest(randomPort = true)
public class WorkflowPropertiesIntegrationTest {

    @Autowired
    private WorkflowProperties workflowProperties;

    @Test
    public void workflowPropertiesContainsHeaders() {
        assertThat(workflowProperties.getWorkflowHeaders().size(), is(equalTo(1)));
        final WorkflowHeader workflowHeader = workflowProperties.getWorkflowHeaders().get(0);
        assertThat(workflowHeader.getName(), is(equalTo("Predix-Zone-Id")));
        assertThat(workflowHeader.getValue(), is(equalTo("xx4747ff-8b69-46a6-a022-2be17dfe0fca")));
    }
}
