package com.ge.digitaltwin.tutorial.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;
import java.util.TreeMap;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnalyticInputControllerTest {

    private AnalyticInputController analyticInputController;

    @Mock
    private QueryService queryService;

    @Mock
    private AnalyticInputService analyticInputService;

    @Before
    public void setup() {
        initMocks(this);
        analyticInputController = new AnalyticInputController(queryService, analyticInputService);
    }

    @Test
    public void getsAnalyticInputForPerformanceOverTime() {
        final TreeMap<Date, Performance> performanceOverTime = new TreeMap<>();
        given(queryService.findPerformanceOverTime("foo", new Date(123L), new Date(234L))).willReturn(performanceOverTime);
        final AnalyticInput expectedAnalyticInput = new AnalyticInput();
        given(analyticInputService.convertPerformanceOverTimeToAnalyticInput(eq("foo"), same(performanceOverTime))).willReturn(expectedAnalyticInput);

        final AnalyticInput actualAnalyticInput = analyticInputController.getAnalyticInputForPerformanceOverTime("foo", new Date(123L), new Date(234L));
        assertThat(actualAnalyticInput, is(sameInstance(expectedAnalyticInput)));
    }

}
