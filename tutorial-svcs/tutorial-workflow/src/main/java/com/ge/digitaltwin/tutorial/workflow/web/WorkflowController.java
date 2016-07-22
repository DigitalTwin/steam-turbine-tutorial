package com.ge.digitaltwin.tutorial.workflow.web;

import com.ge.digitaltwin.tutorial.workflow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@CrossOrigin
@RequestMapping("/workflow")
public class WorkflowController {

    private final WorkflowService service;

    @Autowired
    public WorkflowController(WorkflowService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{workflowId}", method = RequestMethod.POST)
    public Map<String, String> runWorkflow(@PathVariable String workflowId,
                                           @RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody Map<String, Object> parameterMap) {

        final HashMap<String, Object> initialVariables = new HashMap<>();
        if (parameterMap != null)
            initialVariables.putAll(parameterMap);

        if (authorizationHeader != null)
            initialVariables.put("Authorization", authorizationHeader);

        return service.runWorkflow(workflowId, initialVariables);
    }
}
