package com.ge.digitaltwin.tutorial.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Service;

@Service
public class SimulatorService {

    private WorkflowService workflow;

    @Autowired
    public SimulatorService(WorkflowProperties workflowProperties, ClientCredentialsResourceDetails clientCredentialsResourceDetails) {
        workflow = new WorkflowService(workflowProperties, clientCredentialsResourceDetails);
    }

    public void runSimulation(String assetId, Long startTime, Long endTime, Integer intervalMilliseconds) {
        Long workflowStartTime = Long.valueOf(startTime.longValue());
        for (Long time = startTime + intervalMilliseconds; time <= endTime; time += intervalMilliseconds, workflowStartTime += intervalMilliseconds) {
            workflow.invokeWorkflow(assetId, workflowStartTime, time);
        }
    }
}
