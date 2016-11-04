package com.ge.research.starterkit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static com.ge.research.starterkit.ActualVsExpectedEntryPoint.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestEntryPoint {
    private ActualVsExpectedEntryPoint entryPoint;

    @Before
    public void setup() {
        this.entryPoint = new ActualVsExpectedEntryPoint();
    }

    @Test
    public void ParsingComplexJsonStructureWorks() throws IOException {
        final String json = "{\n" +
                "\t\"series\": [{\n" +
                "\t\t\"timestamp\": 1466308800000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 201\n" +
                "\t}, {\n" +
                "\t\t\"timestamp\": 1466308801000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 203\n" +
                "\t}, {\n" +
                "\t\t\"timestamp\": 1466308802000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 208\n" +
                "\t}, {\n" +
                "\t\t\"timestamp\": 1466308803000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 211\n" +
                "\t}],\n" +
                "\t\"slope\": 2.78,\n" +
                "\t\"intercept\": 44.0\n" +
                "}";

        final Map<String, Object> jsonDataMap = entryPoint.parseJson(json);
        assertEquals(jsonDataMap.get(SLOPE), 2.78);
        assertEquals(jsonDataMap.get(INTERCEPT), 44.0);

        final Long timestamp = ((List<Map<String, Long>>)jsonDataMap.get(SERIES)).get(2).get(TIMESTAMP);
        final Long expectedTimestamp = 1466308802000L;
        assertThat(timestamp, is(equalTo(expectedTimestamp)));
    }

    @Test
    public void RunningAnalyticProducesExpectedResults() throws IOException  {
        final ObjectMapper mapper = new ObjectMapper();
        final String json = "{\n" +
                "\t\"series\": [{\n" +
                "\t\t\"timestamp\": 1466308800000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 201\n" +
                "\t}, {\n" +
                "\t\t\"timestamp\": 1466308801000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 203\n" +
                "\t}, {\n" +
                "\t\t\"timestamp\": 1466308802000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 208\n" +
                "\t}, {\n" +
                "\t\t\"timestamp\": 1466308803000,\n" +
                "\t\t\"rpm\": 3000,\n" +
                "\t\t\"actualTemperature\": 211\n" +
                "\t}],\n" +
                "\t\"slope\": 0.065,\n" +
                "\t\"intercept\": 4.0\n" +
                "}";

        final List<Double> expectedDeltaList = new ArrayList<>(Arrays.asList(2.0, 4.0, 9.0, 12.0));

        final String jsonResponse = entryPoint.computeDelta(json);

        final Map<String, Object> jsonDataMap = (Map<String, Object>)mapper.readValue(jsonResponse, HashMap.class).get(RESULT);
        for (int i=0 ; i < expectedDeltaList.size() ; i++) {
            final Double expectedDelta = expectedDeltaList.get(i);
            final List<Map<String, Number>> rpmSeries = (List<Map<String, Number>>) jsonDataMap.get(SERIES);
            final Map<String, Number> node = rpmSeries.get(i);
            final Double actualDelta = node.get(DELTA).doubleValue();
            assertThat(actualDelta, is(equalTo(expectedDelta)));
        }
    }

}
