package com.ge.digitaltwin.tutorial.data;

import com.ge.digitaltwin.tutorial.common.coefficient.ModelCoefficient;
import com.ge.digitaltwin.tutorial.data.AnalyticInput.PerformanceAtTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnalyticInputServiceTest {

    private AnalyticInputService analyticInputService;

    @Mock
    private CoefficientService coefficientService;

    @Before
    public void setup() {
        initMocks(this);
        analyticInputService = new AnalyticInputService(coefficientService);
    }

    @Test
    public void convertsPerformanceOverTimeToAnalyticInput() {
        given(coefficientService.fetchCoefficientForAssetId("foo")).willReturn(new ModelCoefficient());

        final Map<Date, Performance> performanceOverTime = new TreeMap<>();
        performanceOverTime.put(new Date(123L), new Performance(234D, 345D));
        performanceOverTime.put(new Date(1234L), new Performance(2345D, 3456D));

        final AnalyticInput analyticInput = analyticInputService.convertPerformanceOverTimeToAnalyticInput("foo", performanceOverTime);
        final List<PerformanceAtTime> analyticInputSeries = analyticInput.getSeries();
        assertThat(analyticInputSeries.size(), is(2));

        final PerformanceAtTime firstPerformanceAtTime = analyticInputSeries.get(0);
        assertThat(firstPerformanceAtTime.getTimestamp(), is(123L));
        assertThat(firstPerformanceAtTime.getRpm(), is(234D));
        assertThat(firstPerformanceAtTime.getActualTemperature(), is(345D));

        final PerformanceAtTime secondPerformanceAtTime = analyticInputSeries.get(1);
        assertThat(secondPerformanceAtTime.getTimestamp(), is(1234L));
        assertThat(secondPerformanceAtTime.getRpm(), is(2345D));
        assertThat(secondPerformanceAtTime.getActualTemperature(), is(3456D));
    }

    @Test
    public void includesCoefficientsInAnalyticInput() {
        final ModelCoefficient modelCoefficient = new ModelCoefficient();
        modelCoefficient.setB(2d);
        modelCoefficient.setM(4d);

        given(coefficientService.fetchCoefficientForAssetId("foo")).willReturn(modelCoefficient);

        final AnalyticInput analyticInput = analyticInputService.convertPerformanceOverTimeToAnalyticInput("foo", new TreeMap<>());
        assertThat(analyticInput.getSlope(), is(4d));
        assertThat(analyticInput.getIntercept(), is(2d));
    }
}
