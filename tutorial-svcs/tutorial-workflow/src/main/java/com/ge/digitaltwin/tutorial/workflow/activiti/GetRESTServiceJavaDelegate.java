package com.ge.digitaltwin.tutorial.workflow.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Extension of the BaseRESTServiceJavaDelegate implementation for GET requests
 */
public class GetRESTServiceJavaDelegate extends BaseRESTServiceJavaDelegate {

    public GetRESTServiceJavaDelegate() {
        super();
    }

    @Autowired
    public GetRESTServiceJavaDelegate(RestTemplate restTemplate) {
        super(restTemplate);
    }

    /**
     * Executes a REST GET request using the URL attribute value and
     * looks up the PARAMS_KEY variable in the execution scope for arguments to the REST service
     * The value of the PARAMS_KEY is expected to be a Map<String, Object>.
     * If it is not, or it is not found, then no arguments are passed with the GET request
     * The Map keys need to match the placeholder values in the URL, in order to be correctly replaced
     * example http://site.domain/serviceName?attribute={mapKeyName}
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

            final ResponseEntity<Object> serviceResponse = doGet(getURLasString(),
                    new HttpEntity<>(createHttpHeaders(delegateExecution)),
                    getProcessVariable(delegateExecution, PARAMS_KEY));

            updateDataKeyVariable(delegateExecution, serviceResponse);
        }
    }

}
