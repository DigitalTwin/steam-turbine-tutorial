package com.ge.digitaltwin.tutorial.simulator;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import com.google.gson.Gson;

import static org.apache.commons.io.IOUtils.write;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkflowService {

    private final WorkflowProperties workflowProperties;
    private final ClientCredentialsResourceDetails clientCredentialsResourceDetails;

    @Autowired
    public WorkflowService(WorkflowProperties workflowProperties, ClientCredentialsResourceDetails clientCredentialsResourceDetails) {
        this.workflowProperties = workflowProperties;
        this.clientCredentialsResourceDetails = clientCredentialsResourceDetails;
    }

    public String invokeWorkflow(String assetId, Long startTime, Long endTime) {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails).execute(
                workflowProperties.getWorkflowUri(), workflowProperties.getWorkflowMethod(),
                request -> createRequest(request, workflowProperties.getWorkflowBodyTag(), assetId, startTime, endTime), WorkflowService::extractData);
    }

    private static String extractData(ClientHttpResponse response) throws IOException {
        return IOUtils.toString(response.getBody());
    }

    private void createRequest(ClientHttpRequest request, String bodyTag, String assetId, Long startTime, Long endTime) throws IOException {
        if (workflowProperties.getWorkflowHeaders() != null) {
            for (WorkflowHeader workflowHeader : workflowProperties.getWorkflowHeaders()) {
                request.getHeaders().set(workflowHeader.getName(), workflowHeader.getValue());
            }
        }
        final Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("assetId", assetId);
        paramValues.put("startTime", startTime);
        paramValues.put("endTime", endTime);
        final Map<String, Map<String, Object>> body = new HashMap<>();
        body.put(bodyTag, paramValues);
        Gson gson = new Gson();
        write(gson.toJson(body), request.getBody());
    }

}
