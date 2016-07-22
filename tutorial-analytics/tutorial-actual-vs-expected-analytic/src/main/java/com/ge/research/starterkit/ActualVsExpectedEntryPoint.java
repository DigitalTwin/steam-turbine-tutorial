package com.ge.research.starterkit;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ActualVsExpectedEntryPoint {

    public static final String SLOPE = "slope";
    public static final String INTERCEPT = "intercept";
    public static final String SERIES = "series";
    public static final String RPM = "rpm";
    public static final String ACTUAL = "actualTemperature";
    public static final String EXPECTED = "expectedTemperature";
    public static final String DELTA = "delta";
    public static final String TIMESTAMP = "timestamp";
    public static final String RESULT = "result";


	public String computeDelta(String jsonStr) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Object> jsonDataMap = mapper.readValue(jsonStr, Map.class);
        final List<Map<String, Number>> rpmSeries = (List<Map<String, Number>>)jsonDataMap.get(SERIES);

        final BigDecimal slope = new BigDecimal("" + jsonDataMap.get(SLOPE));
        final BigDecimal intercept = new BigDecimal("" + jsonDataMap.get(INTERCEPT));

        for (Map<String, Number> node : rpmSeries) {
            final BigDecimal rpm = new BigDecimal("" + node.get(RPM));
            final BigDecimal actual = new BigDecimal("" + node.get(ACTUAL));
            final BigDecimal expected = slope.multiply(rpm).add(intercept);
            final BigDecimal delta = actual.subtract(expected);

            node.put(EXPECTED, expected.doubleValue());
            node.put(DELTA, delta.doubleValue());
        }

		final ActualVsExpectedResponse output = new ActualVsExpectedResponse();
		output.setResult(jsonDataMap);

		return mapper.writeValueAsString(output);
    }

    public Map<String, Object> parseJson(String jsonStr) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, HashMap.class);
    }
}