package com.ge.digitaltwin.tutorial.workflow.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Abstract base class for all REST service implementations to extend from
 * Provides common functionality for storing and processing the service URL,
 * and for retrieving variable values from the workflow process memory scope
 */
abstract class BaseRESTServiceJavaDelegate implements JavaDelegate {

    static final String DATA_KEY = "data";
    static final String PARAMS_KEY = "params";
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String PREDIX_ZONE_ID = "Predix-Zone-Id";

    private final RestTemplate restTemplate;

    private Expression url = null;
    private Expression predixZoneId = null;

    private static final Logger LOGGER = getLogger(BaseRESTServiceJavaDelegate.class);

    public BaseRESTServiceJavaDelegate() {
        restTemplate = new RestTemplate();
    }

    public BaseRESTServiceJavaDelegate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public abstract void execute(DelegateExecution delegateExecution);

    HttpHeaders createHttpHeaders(DelegateExecution delegateExecution) {
        final HttpHeaders headers = new HttpHeaders();
        final String zoneId = this.getPredixZoneIdAsString();
        if( zoneId != null ) {
            headers.set(PREDIX_ZONE_ID, zoneId);
        }

        final Object token = this.getProcessVariable(delegateExecution, AUTHORIZATION_KEY);
        if( token != null ) {
            headers.set(AUTHORIZATION_KEY, token.toString());
        }

        return headers;
    }

    ResponseEntity<Object> doGet(String urlString, HttpEntity entity, Object params) {
        return doRequest(urlString, HttpMethod.GET, entity, params);
    }

    ResponseEntity<Object> doPost(String urlString, HttpEntity entity, Object params) {
        return doRequest(urlString, HttpMethod.POST, entity, params);
    }

    void updateDataKeyVariable(DelegateExecution delegateExecution, ResponseEntity<Object> serviceResponse) {
        Object value = null;
        if (serviceResponse != null)
            value = serviceResponse.getBody();
        setProcessVariable(delegateExecution, DATA_KEY, value);
    }

    @SuppressWarnings("unchecked")
    private  ResponseEntity<Object> doRequest(String urlString, HttpMethod method, HttpEntity entity, Object params) {
        ResponseEntity<Object> responseEntity = null;
        try {
            if (params instanceof Map) {
                responseEntity = this.restTemplate.exchange(urlString, method, entity, Object.class, (Map<String, ?>) params);
            } else {
                responseEntity = this.restTemplate.exchange(urlString, method, entity, Object.class);
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("An error occurred while attempting to process request to URL: " + urlString + ", in method: " + method.toString(), e);
            throw e;
        }
        return responseEntity;
    }

    /**
     * Convenience method to check for the existence of a variable in the scope of the @DelegateExecution
     * and if found, return its current value
     *
     * @param delegateExecution the execution scope from which to search for the variable named @param variableName
     * @param variableName the variable name to look for in the process scope and return the value of
     * @return the value of the variable defined by @param variableName; null if no such variable can be found
     */
    Object getProcessVariable(DelegateExecution delegateExecution, String variableName) {
        if (delegateExecution != null && delegateExecution.hasVariable(variableName))
            return delegateExecution.getVariable(variableName);

        return null;
    }

    /**
     * Convenience method to set a value of a variable in the scope of the @DelegateExecution
     *
     * @param delegateExecution the execution scope from which to write the variable named @param variableName
     * @param variableName the variable name to look for in the process scope and set the value of
     * @param variableValue the value to assign tot he variable in the process scope
     */
    void setProcessVariable(DelegateExecution delegateExecution, String variableName, Object variableValue) {
        if (delegateExecution == null)
            throw new IllegalArgumentException("delegate execution is null");
        delegateExecution.setVariable(variableName, variableValue);
    }

    /**
     *
     * @return the value of the URL attribute as a java.lang.String
     * @throws IllegalArgumentException if the URL attribute is null
     */
    String getURLasString() {
        if (url == null)
            throw new IllegalArgumentException("Expression variable URL is null");
        return url.getExpressionText();
    }

    /**
     *
     * @return the value of the predixZoneId attribute as a java.lang.String
     * or null if that field was not set.
    */
    private String getPredixZoneIdAsString() {
        if (predixZoneId != null)
            return predixZoneId.getExpressionText();
        return null;
    }

}
