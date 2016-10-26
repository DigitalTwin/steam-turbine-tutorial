package com.ge.digitaltwin.tutorial.data;

import com.ge.dt.tsc.QueryClient;
import com.ge.dt.tsc.QueryRequest;
import com.ge.dt.tsc.QueryRequest.Tag.Filter;
import com.ge.dt.tsc.QueryResponse;
import com.ge.dt.tsc.QueryResponse.Tag;
import com.ge.dt.tsc.QueryResponse.Tag.Result.DataPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.ge.digitaltwin.tutorial.data.QueryService.TagMetaData.RPM;
import static com.ge.digitaltwin.tutorial.data.QueryService.TagMetaData.TEMPERATURE;
import static java.util.Arrays.asList;

@Service
public class QueryService {

    private static final String ASSET_ID_ATTRIBUTE = "SteamTurbineId";
    private static final long TAG_LIMIT = 1000L;

    private final QueryClient queryClient;

    @Autowired
    public QueryService(QueryClient queryClient) {
        this.queryClient = queryClient;
    }

    public Map<Date, Performance> findPerformanceOverTime(String assetId, Date startTimestamp, Date endTimestamp) {
        final Map<Date, Performance> performanceMap = new TreeMap<>();

        final QueryRequest queryRequest = new QueryRequest(startTimestamp.getTime(), endTimestamp.getTime());
        queryRequest.getTags().add(new QueryRequest.Tag(asList(RPM.getTagName(), TEMPERATURE.getTagName())));
        queryRequest.getTags().stream().forEach(tag -> tag.setFilter(createTagFilters(assetId)));
        queryRequest.getTags().stream().forEach(tag -> tag.setLimit(TAG_LIMIT));

        final QueryResponse queryResponse = queryClient.query(queryRequest);
        mapTagValues(performanceMap, queryResponse, RPM);
        mapTagValues(performanceMap, queryResponse, TEMPERATURE);

        return performanceMap;
    }

    private static Filter createTagFilters(String assetId) {
        final Filter filter = new Filter();
        filter.setAttributes(new HashMap<>());
        filter.getAttributes().put(ASSET_ID_ATTRIBUTE, assetId);
        return filter;
    }

    private static void mapTagValues(Map<Date, Performance> performanceMap, QueryResponse queryResponse, TagMetaData tagMetaData) {
        queryResponse.getTags().stream().filter(tag -> tag.getName().equals(tagMetaData.getTagName())).findFirst()
                .ifPresent(tag -> mapTagValues(performanceMap, tag, tagMetaData.getPerformanceValueMapper()));
    }

    private static void mapTagValues(Map<Date, Performance> performanceMap, Tag tag, PerformanceValueMapper performanceValueMapper) {
        tag.getResults().forEach(result -> result.getValues().stream().forEach(
                dataPoint -> mapDataPoint(performanceMap, dataPoint, performanceValueMapper)));
    }

    private static void mapDataPoint(Map<Date, Performance> performanceMap, DataPoint dataPoint, PerformanceValueMapper performanceValueMapper) {
        performanceMap.putIfAbsent(dataPoint.getTimestamp(), new Performance());
        performanceValueMapper.mapPerformanceValue(performanceMap, dataPoint);
    }

    private static void mapRpmPerformance(Map<Date, Performance> performanceMap, DataPoint dataPoint) {
        performanceMap.get(dataPoint.getTimestamp()).setRpm(dataPoint.getMeasure());
    }

    private static void mapTemperaturePerformance(Map<Date, Performance> performanceMap, DataPoint dataPoint) {
        performanceMap.get(dataPoint.getTimestamp()).setActualTemperature(dataPoint.getMeasure());
    }

    @FunctionalInterface
    private interface PerformanceValueMapper {
        void mapPerformanceValue(Map<Date, Performance> performanceMap, DataPoint dataPoint);
    }

    enum TagMetaData {
        RPM("rpm", QueryService::mapRpmPerformance), TEMPERATURE("actualTemperature", QueryService::mapTemperaturePerformance);

        private String tagName;
        private PerformanceValueMapper performanceValueMapper;

        TagMetaData(String tagName, PerformanceValueMapper performanceValueMapper) {
            this.tagName = tagName;
            this.performanceValueMapper = performanceValueMapper;
        }

        public String getTagName() {
            return tagName;
        }

        public PerformanceValueMapper getPerformanceValueMapper() {
            return performanceValueMapper;
        }
    }

}
