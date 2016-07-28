package com.ge.digitaltwin.tutorial.workflow.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Extension of the BaseRESTServiceJavaDelegate implementation for POST requests
 */
public class PostRESTServiceJavaDelegate extends BaseRESTServiceJavaDelegate {

    public PostRESTServiceJavaDelegate() {
        super();
    }

    @Autowired
    public PostRESTServiceJavaDelegate(RestTemplate restTemplate) {
        super(restTemplate);
    }

    /**
     * Executes a REST POST request using the URL attribute value and
     * looks up the DATA_KEY variable in the execution scope as the payload to the REST service
     * Upon successful invocation of the REST service, the returned data is stored
     * in the execution scope under the DATA_KEY variable, overwriting any value already there.
     * If no data is returned by the service, then the DATA_KEY variable will be set to null
     *
     * @param delegateExecution the execution scope from which to read and write data for the workflow process use
     * @throws org.springframework.web.client.HttpClientErrorException should the REST service throw such an exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) {
        if (delegateExecution != null) {
            final ResponseEntity<Object> serviceResponse = doPost(getURLasString(),
                    new HttpEntity<>(getProcessVariable(delegateExecution, DATA_KEY), createHttpHeaders(delegateExecution)),
                    getProcessVariable(delegateExecution, PARAMS_KEY));

            updateDataKeyVariable(delegateExecution, serviceResponse);
        }
    }
}
