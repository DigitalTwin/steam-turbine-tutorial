package com.ge.digitaltwin.tutorial.result;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnalyticResultTest {

    private AnalyticResult analyticResult;

    @Before
    public void setup() {
        analyticResult = new AnalyticResult();
    }

    @Test
    public void returnsTemperatureDelta() {
        analyticResult.setActualTemperature(7);
        analyticResult.setExpectedTemperature(2);
        assertThat(analyticResult.getTemperatureDelta(), is(equalTo(5d)));
    }

}
