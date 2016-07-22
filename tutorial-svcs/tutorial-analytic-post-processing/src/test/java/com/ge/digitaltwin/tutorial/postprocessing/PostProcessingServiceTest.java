package com.ge.digitaltwin.tutorial.postprocessing;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class PostProcessingServiceTest {

    private JSONObject formExpectedJSONStructure(JSONObject... dataPoints) throws JSONException {
        final JSONArray array = new JSONArray();

        for (JSONObject dataPoint : dataPoints) {
            array.put(dataPoint);
        }

        final JSONObject rpmValues = new JSONObject();
        rpmValues.put("series", array);

        final JSONObject result = new JSONObject();
        result.put("result", rpmValues);

        return result;
    }

    @Test
    public void transformRawAnalyticOutputShouldProduceCorrectAnalyticResultObjects() throws JSONException {
        final JSONObject timeSeriesValue1 = new JSONObject();
        timeSeriesValue1.put("timestamp", "1467744926000");
        timeSeriesValue1.put("expectedTemperature", 200);
        timeSeriesValue1.put("actualTemperature", 205);
        timeSeriesValue1.put("rpm", 3000);

        final JSONObject timeSeriesValue2 = new JSONObject();
        timeSeriesValue2.put("timestamp", "1467831439000");
        timeSeriesValue2.put("expectedTemperature", 200);
        timeSeriesValue2.put("actualTemperature", 208);
        timeSeriesValue2.put("rpm", 3000);

        final JSONObject result = formExpectedJSONStructure(timeSeriesValue1, timeSeriesValue2);

        final List<AnalyticResult> analyticResults = PostProcessingService.transformRawAnalyticOutput("10", result.toString());

        assertThat(analyticResults, hasItem(equalTo(new AnalyticResult(200, 205, new Date(1467744926000L), "10", 3000))));
        assertThat(analyticResults, hasItem(equalTo(new AnalyticResult(200, 208, new Date(1467831439000L), "10", 3000))));
        assertThat(analyticResults, hasSize(equalTo(2)));
    }

    @Test(expected = InvalidAnalyticResultsException.class)
    public void transformRayAnalyticOutputShouldThrowErrorWhenJSONMissingValues() throws JSONException {
        final JSONObject timeSeriesValue1 = new JSONObject();

        final JSONObject result = formExpectedJSONStructure(timeSeriesValue1);

        PostProcessingService.transformRawAnalyticOutput("10", result.toString());
    }

    @Test(expected = InvalidAnalyticResultsException.class)
    public void transformRayAnalyticOutputShouldThrowErrorWhenJSONMissingTimeStampValue() throws JSONException {
        final JSONObject timeSeriesValue1 = new JSONObject();
        timeSeriesValue1.put("expectedTemperature", 200);
        timeSeriesValue1.put("actualTemperature", 205);
        timeSeriesValue1.put("rpm", 3000);

        final JSONObject result = formExpectedJSONStructure(timeSeriesValue1);

        PostProcessingService.transformRawAnalyticOutput("10", result.toString());
    }

    @Test(expected = InvalidAnalyticResultsException.class)
    public void transformRayAnalyticOutputShouldThrowErrorWhenJSONMissingExpectedTemperatureValue() throws JSONException {
        final JSONObject timeSeriesValue1 = new JSONObject();
        timeSeriesValue1.put("timestamp", "1467831439000");
        timeSeriesValue1.put("actualTemperature", 205);
        timeSeriesValue1.put("rpm", 3000);

        final JSONObject result = formExpectedJSONStructure(timeSeriesValue1);

        PostProcessingService.transformRawAnalyticOutput("10", result.toString());
    }

    @Test(expected = InvalidAnalyticResultsException.class)
    public void transformRayAnalyticOutputShouldThrowErrorWhenJSONMissingActualTemperatureValue() throws JSONException {
        final JSONObject timeSeriesValue1 = new JSONObject();
        timeSeriesValue1.put("timestamp", "1467831439000");
        timeSeriesValue1.put("expectedTemperature", 200);
        timeSeriesValue1.put("rpm", 3000);

        final JSONObject result = formExpectedJSONStructure(timeSeriesValue1);

        PostProcessingService.transformRawAnalyticOutput("10", result.toString());
    }

    @Test(expected = InvalidAnalyticResultsException.class)
    public void transformRayAnalyticOutputShouldThrowErrorWhenJSONMissingRpmValue() throws JSONException {
        final JSONObject timeSeriesValue1 = new JSONObject();
        timeSeriesValue1.put("timestamp", "1467831439000");
        timeSeriesValue1.put("actualTemperature", 205);
        timeSeriesValue1.put("expectedTemperature", 200);

        final JSONObject result = formExpectedJSONStructure(timeSeriesValue1);

        PostProcessingService.transformRawAnalyticOutput("10", result.toString());
    }

    @Test(expected = InvalidAnalyticResultsException.class)
    public void transformRayAnalyticOutputShouldThrowErrorWhenJSONHasWrongStructure() throws JSONException {
        final JSONObject timeSeriesValue1 = new JSONObject();
        timeSeriesValue1.put("timestamp", "1467831439000");
        timeSeriesValue1.put("actualTemperature", 205);
        timeSeriesValue1.put("expectedTemperature", 200);
        timeSeriesValue1.put("rpm", 3000);

        final JSONObject result = new JSONObject();
        result.put("result", timeSeriesValue1);

        PostProcessingService.transformRawAnalyticOutput("10", result.toString());
    }

    @Test
    public void transformRayAnalyticOutputShouldReturnEmptyArrayWhenNoDataPointsPresent() throws JSONException {
        final List<AnalyticResult> analyticResults = PostProcessingService.transformRawAnalyticOutput("10", formExpectedJSONStructure().toString());

        assertThat(analyticResults, hasSize(equalTo(0)));
    }

}