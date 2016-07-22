package com.ge.digitaltwin.tutorial.workflow.service;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

    private RuntimeService runtimeService;

    @Autowired
    public WorkflowService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    /**
     * Initiates the workflow process and returns the runtime identification information for the started process
     *
     * @param workflowId the key of the activiti workflow to execute
     * @param varMap the data variables to make available to the workflow
     * @return a Map containing the process instance identification information as key,value pairs
     */
    public Map<String, String> runWorkflow(String workflowId, Map<String, Object> varMap) {
        return getProcessInstanceInformationDetails(runtimeService.startProcessInstanceByKey(workflowId, varMap));
    }

    private static Map<String, String> getProcessInstanceInformationDetails(ProcessInstance pi) {
        final Map<String, String> processInstanceInformation = new HashMap<>();
        if (pi != null) {
            processInstanceInformation.put("Execution ID", pi.getId());
            processInstanceInformation.put("Deployment ID", pi.getDeploymentId());
            processInstanceInformation.put("Process Instance ID", pi.getProcessInstanceId());
            processInstanceInformation.put("Process Definition ID", pi.getProcessDefinitionId());
            processInstanceInformation.put("Process Definition Key", pi.getProcessDefinitionKey());
            processInstanceInformation.put("Process Definition Name", pi.getProcessDefinitionName());
            processInstanceInformation.put("Business Key", pi.getBusinessKey());
            processInstanceInformation.put("Name", pi.getName());
            processInstanceInformation.put("Parent ID", pi.getParentId());
        }
        return processInstanceInformation;
    }

}
