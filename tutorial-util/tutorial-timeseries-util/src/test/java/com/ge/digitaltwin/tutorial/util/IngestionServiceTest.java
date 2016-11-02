package com.ge.digitaltwin.tutorial.util;

import com.ge.dt.tsc.*;
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
    private IngestionClient digitalTwinTimeSeriesClient;

    @Mock
    private IngestionSession digitalTwinTimeSeriesSession;

    @Before
    public void setup() {
        initMocks(this);
        ingestionService = new IngestionService(digitalTwinTimeSeriesClient);
    }

    @Test
    public void pushesTurbineDataPointToDigitalTwinTimeseries() throws DigitalTwinTimeSeriesClientException {
        final SteamTurbineDataPoint steamTurbineDataPoint = new SteamTurbineDataPoint();
        steamTurbineDataPoint.setSteamTurbineId("test-42");
        steamTurbineDataPoint.setTimestamp(new java.sql.Date(1461684000000L));
        steamTurbineDataPoint.setActualTemperature(98.6);
        steamTurbineDataPoint.setRpm(5000.0);

        ingestionService.ingest(singletonList(steamTurbineDataPoint));

        final ArgumentCaptor<IngestionJob> digitalTwinTimeSeriesJobArgumentCaptor = ArgumentCaptor.forClass(IngestionJob.class);
        then(digitalTwinTimeSeriesClient).should().doInSession(digitalTwinTimeSeriesJobArgumentCaptor.capture());
        digitalTwinTimeSeriesJobArgumentCaptor.getValue().doInSession(digitalTwinTimeSeriesSession);

        final ArgumentCaptor<IngestionRequest> ingestionRequestArgumentCaptor = ArgumentCaptor.forClass(IngestionRequest.class);
        then(digitalTwinTimeSeriesSession).should(times(1)).ingest(ingestionRequestArgumentCaptor.capture());
        final IngestionRequest ingestionRequest = ingestionRequestArgumentCaptor.getValue();

        assertThat(ingestionRequest.getBodies().get(0).getAttributes().get("SteamTurbineId"), is(equalTo(steamTurbineDataPoint.getSteamTurbineId())));
        assertThat(ingestionRequest.getBodies().get(0).getDataPoints().get(0).getMeasure(), is(equalTo(steamTurbineDataPoint.getActualTemperature())));
        assertThat(ingestionRequest.getBodies().get(1).getDataPoints().get(0).getMeasure(), is(equalTo(steamTurbineDataPoint.getRpm())));
    }

}
