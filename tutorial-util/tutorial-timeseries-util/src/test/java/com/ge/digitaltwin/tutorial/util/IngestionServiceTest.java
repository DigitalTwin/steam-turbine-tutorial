package com.ge.digitaltwin.tutorial.util;

import com.ge.dt.ptsc.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

public class IngestionServiceTest {
    private IngestionService ingestionService;

    @Mock
    private IngestionClient predixTimeSeriesClient;

    @Mock
    private IngestionSession predixTimeSeriesSession;

    @Before
    public void setup() {
        initMocks(this);
        ingestionService = new IngestionService(predixTimeSeriesClient);
    }

    @Test
    public void pushesTurbineDataPointToPredixTimeseries() throws PredixTimeSeriesClientException {
        final SteamTurbineDataPoint steamTurbineDataPoint = new SteamTurbineDataPoint();
        steamTurbineDataPoint.setSteamTurbineId("test-42");
        steamTurbineDataPoint.setTimestamp(new java.sql.Date(1461684000000L));
        steamTurbineDataPoint.setActualTemperature(98.6);
        steamTurbineDataPoint.setRpm(5000.0);

        ingestionService.ingest(singletonList(steamTurbineDataPoint));

        final ArgumentCaptor<IngestionJob> predixTimeSeriesJobArgumentCaptor = ArgumentCaptor.forClass(IngestionJob.class);
        then(predixTimeSeriesClient).should().doInSession(predixTimeSeriesJobArgumentCaptor.capture());
        predixTimeSeriesJobArgumentCaptor.getValue().doInSession(predixTimeSeriesSession);

        final ArgumentCaptor<IngestionRequest> ingestionRequestArgumentCaptor = ArgumentCaptor.forClass(IngestionRequest.class);
        then(predixTimeSeriesSession).should(times(1)).ingest(ingestionRequestArgumentCaptor.capture());
        final IngestionRequest ingestionRequest = ingestionRequestArgumentCaptor.getValue();

        assertThat(ingestionRequest.getBodies().get(0).getAttributes().get("SteamTurbineId"), is(equalTo(steamTurbineDataPoint.getSteamTurbineId())));
        assertThat(ingestionRequest.getBodies().get(0).getDataPoints().get(0).getMeasure(), is(equalTo(steamTurbineDataPoint.getActualTemperature())));
        assertThat(ingestionRequest.getBodies().get(1).getDataPoints().get(0).getMeasure(), is(equalTo(steamTurbineDataPoint.getRpm())));
    }

}
