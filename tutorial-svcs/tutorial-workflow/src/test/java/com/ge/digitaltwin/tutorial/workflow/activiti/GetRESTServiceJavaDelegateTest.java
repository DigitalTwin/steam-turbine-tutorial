package com.ge.digitaltwin.tutorial.workflow.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.FixedValue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.ge.digitaltwin.tutorial.workflow.activiti.BaseRESTServiceJavaDelegate.DATA_KEY;
import static com.ge.digitaltwin.tutorial.workflow.activiti.BaseRESTServiceJavaDelegate.PARAMS_KEY;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class GetRESTServiceJavaDelegateTest {
    private GetRESTServiceJavaDelegate getDelegate;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DelegateExecution delegateExecution;

    @Before
    public void setup() {
        initMocks(this);
        getDelegate = new GetRESTServiceJavaDelegate(restTemplate);
    }

    @Test
    public void executesHttpGETRequest() {
        setField(getDelegate, "url", new FixedValue("foo"));
        final ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        given(responseEntity.getBody()).willReturn("bar");
        given(restTemplate.exchange(any(String.class), eq(GET), any(HttpEntity.class), eq(Object.class))).willReturn(responseEntity);
        doThrow(RuntimeException.class).when(delegateExecution).setVariable(eq(DATA_KEY), eq("bar"));

        try {
            getDelegate.execute(delegateExecution);
            fail("Expected RuntimeException to be thrown by mock");
        } catch (IllegalArgumentException iae) {
            fail(iae.getMessage());
        } catch (RuntimeException re) {
            assertTrue(true);
        }
    }

    @Test
    public void executesHttpGETRequestWithTemplateReplacement() {
        setField(getDelegate, "url", new FixedValue("foo"));
        final ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        given(responseEntity.getBody()).willReturn("bar");
        given(restTemplate.exchange(any(String.class), eq(GET), any(HttpEntity.class), eq(Object.class), any(Map.class))).willReturn(responseEntity);
        given(delegateExecution.hasVariable(PARAMS_KEY)).willReturn(true);
        given(delegateExecution.getVariable(PARAMS_KEY)).willReturn(new HashMap<String, Object>());
        doThrow(RuntimeException.class).when(delegateExecution).setVariable(eq(DATA_KEY), eq("bar"));

        try {
            getDelegate.execute(delegateExecution);
            fail("Expected RuntimeException to be thrown by mock");
        } catch (IllegalArgumentException iae) {
            fail(iae.getMessage());
        } catch (RuntimeException re) {
            assertTrue(true);
        }
    }

    @Test
    public void doesNotExecutesHttpRequestIfDelegateIsNull() {
        doThrow(RuntimeException.class).when(restTemplate).exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(Object.class));
        doThrow(RuntimeException.class).when(restTemplate).exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(Object.class), any(Map.class));
        try {
            getDelegate.execute(null);
            assertTrue(true);
        } catch (RuntimeException re) {
            fail("Did not expect RuntimeException to be thrown by mock");
        }
    }
}
