package com.ge.digitaltwin.tutorial.postprocessing;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostProcessingService {
    private static final String RESULT_ROOT_KEY = "result";
    private static final String SERIES_ROOT_KEY = "series";

    private PostProcessingService() {

    }

    public static List<AnalyticResult> transformRawAnalyticOutput(String assetId, String rawAnalyticOutput) {
        final ArrayList<AnalyticResult> analyticResultArrayList = new ArrayList<>();

        try {
            final JSONObject resultObject = new JSONObject(rawAnalyticOutput);
            final JSONArray entryArray = resultObject.getJSONObject(RESULT_ROOT_KEY).getJSONArray(SERIES_ROOT_KEY);

            for (int i = 0; i < entryArray.length(); i++) {
                final JSONObject entry = entryArray.getJSONObject(i);
                final double expectedTemperature = entry.getDouble("expectedTemperature");
                final double actualTemperature = entry.getDouble("actualTemperature");
                final Date timestamp = new Date(new Long(entry.getString("timestamp")));
                final int rpm = entry.getInt("rpm");
                analyticResultArrayList.add(new AnalyticResult(expectedTemperature, actualTemperature, timestamp, assetId, rpm));
            }

        } catch (Exception e) {
            throw new InvalidAnalyticResultsException("Encountered exception when transforming analytic results", e);
        }

        return analyticResultArrayList;
    }
}
