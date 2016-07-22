package com.ge.digitaltwin.tutorial.simulator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Component
@ConfigurationProperties("tutorial.workflow")
public class WorkflowProperties {

    @NotNull
    private URI workflowUri;

    @NotNull
    private HttpMethod workflowMethod;

    private List<WorkflowHeader> workflowHeaders;

    private String workflowBodyTag;

    public void setWorkflowUri(URI workflowUri) {
        this.workflowUri = workflowUri;
    }

    public URI getWorkflowUri() {
        return workflowUri;
    }

    public void setWorkflowMethod(HttpMethod workflowMethod) {
        this.workflowMethod = workflowMethod;
    }

    public HttpMethod getWorkflowMethod() {
        return workflowMethod;
    }

    public List<WorkflowHeader> getWorkflowHeaders() {
        return workflowHeaders;
    }

    public void setWorkflowHeaders(List<WorkflowHeader> workflowHeaders) {
        this.workflowHeaders = workflowHeaders;
    }

    public String getWorkflowBodyTag() {
        return this.workflowBodyTag;
    }

    public void setWorkflowBodyTag(String workflowBodyTag) {
        this.workflowBodyTag = workflowBodyTag;
    }

}
