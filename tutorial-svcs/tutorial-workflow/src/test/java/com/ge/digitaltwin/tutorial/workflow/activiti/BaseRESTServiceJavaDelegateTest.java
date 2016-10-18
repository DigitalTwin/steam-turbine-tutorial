package com.ge.digitaltwin.tutorial.workflow.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.FixedValue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class BaseRESTServiceJavaDelegateTest {
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String PREDIX_ZONE_ID = "Predix-Zone-Id";
    private BaseRESTServiceJavaDelegate baseDelegate;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DelegateExecution delegateExecution;

    @Before
    public void setup() {
        initMocks(this);

        baseDelegate = new BaseRESTServiceJavaDelegate(restTemplate) {
            @Override
            public void execute(DelegateExecution execution) {
                throw new UnsupportedOperationException("Execution of test implementation not supported");
            }
        };
    }

    @Test
    @Ignore
    public void setsAuthorizationHeaderOfRequest() {
        given(delegateExecution.hasVariable(AUTHORIZATION_KEY)).willReturn(true);
        given(delegateExecution.getVariable(AUTHORIZATION_KEY)).willReturn("foo");
        HttpHeaders headers = baseDelegate.createHttpHeaders(delegateExecution);
        assertThat(headers.getFirst("Authorization"), is(equalTo("foo")));
    }

    @Test
    @Ignore
    public void authorizationHeaderNotSetIfNotProvided() throws Exception {
        given(delegateExecution.hasVariable(AUTHORIZATION_KEY)).willReturn(false);
        HttpHeaders headers = baseDelegate.createHttpHeaders(delegateExecution);
        assertThat(headers.get("Authorization"), is(nullValue()));
    }

    @Test
    public void setsPredixZoneIdOfRequest() {
        setField(baseDelegate, "predixZoneId", new FixedValue("foo"));
        HttpHeaders headers = baseDelegate.createHttpHeaders(delegateExecution);
        assertThat(headers.getFirst(PREDIX_ZONE_ID), is(equalTo("foo")));
    }

    @Test
    public void predixZoneIdNotSetIfNotProvided() throws Exception {
        HttpHeaders headers = baseDelegate.createHttpHeaders(delegateExecution);
        assertThat(headers.get(PREDIX_ZONE_ID), is(nullValue()));
    }

    @Test
    public void executesHttpGETRequest() {
        final ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        given(restTemplate.exchange(any(String.class), eq(GET), any(HttpEntity.class), eq(Object.class))).willReturn(responseEntity);

        assertThat(baseDelegate.doGet("foo", new HttpEntity<>(new HttpHeaders()), null), is(equalTo(responseEntity)));
    }

    @Test
    public void executesHttpPOSTRequest() {
        final ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        given(restTemplate.exchange(any(String.class), eq(POST), any(HttpEntity.class), eq(Object.class))).willReturn(responseEntity);

        assertThat(baseDelegate.doPost("foo", new HttpEntity<>(new HttpHeaders()), null), is(equalTo(responseEntity)));
    }

    @Test
    public void updateDataKeyVariableWithResponseBody() {
        final ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        given(responseEntity.getBody()).willReturn("foo");
        doThrow(RuntimeException.class).when(delegateExecution).setVariable(eq(BaseRESTServiceJavaDelegate.DATA_KEY), isNotNull());
        try {
            baseDelegate.updateDataKeyVariable(delegateExecution, responseEntity);
            fail("Expected RuntimeException to be thrown by mock");
        } catch (RuntimeException re) {
            assertNotNull(re);
        }
    }

    @Test
    public void noResponseSetsDataKeyVariableToNull() {
        doThrow(RuntimeException.class).when(delegateExecution).setVariable(eq(BaseRESTServiceJavaDelegate.DATA_KEY), isNull());
        try {
            baseDelegate.updateDataKeyVariable(delegateExecution, null);
            fail("Expected RuntimeException to be thrown by mock");
        } catch (RuntimeException re) {
            assertNotNull(re);
        }
    }
}
