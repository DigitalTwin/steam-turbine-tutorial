package com.ge.digitaltwin.tutorial.data;

import com.ge.dt.tsc.QueryClient;
import com.ge.dt.tsc.QueryRequest;
import com.ge.dt.tsc.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

public class QueryServiceTest {

    private QueryService queryService;

    @Mock
    private QueryClient queryClient;

    @Before
    public void setup() {
        initMocks(this);
        queryService = new QueryService(queryClient);

        final QueryResponse queryResponse = new QueryResponse() {{
            setTags(new ArrayList<>());
            getTags().add(new Tag() {{
                setName("rpm");
                setResults(new ArrayList<>());
                getResults().add(new Result() {{
                    setValues(new ArrayList<>());
                    getValues().add(new DataPoint(new Date(100L), 12345D, 1));
                }});
            }});

            getTags().add(new Tag() {{
                setName("actualTemperature");
                setResults(new ArrayList<>());
                getResults().add(new Result() {{
                    setValues(new ArrayList<>());
                    getValues().add(new DataPoint(new Date(100L), 234D, 1));
                }});
            }});
        }};

        given(queryClient.query(any(QueryRequest.class))).willReturn(queryResponse);
    }

    @Test
    public void mapsTimeSeriesData() {
        final Map<Date, Performance> performanceMap = queryService.findPerformanceOverTime("foo", new Date(0L), new Date(123L));
        assertThat(performanceMap.containsKey(new Date(100L)), is(true));
        assertThat(performanceMap.get(new Date(100L)).getRpm(), is(12345D));
        assertThat(performanceMap.get(new Date(100L)).getActualTemperature(), is(234D));
    }

    @Test
    public void queriesTimeSeriesWithDateRange() {
        queryService.findPerformanceOverTime("foo", new Date(0L), new Date(123L));

        final ArgumentCaptor<QueryRequest> queryRequestArgumentCaptor = ArgumentCaptor.forClass(QueryRequest.class);
        then(queryClient).should().query(queryRequestArgumentCaptor.capture());

        final QueryRequest queryRequest = queryRequestArgumentCaptor.getValue();
        assertThat(queryRequest.getStart(), is(0L));
        assertThat(queryRequest.getEnd(), is(123L));
    }

    @Test
    public void queriesTimeSeriesWithTagNames() {
        queryService.findPerformanceOverTime("foo", new Date(0L), new Date(123L));

        final ArgumentCaptor<QueryRequest> queryRequestArgumentCaptor = ArgumentCaptor.forClass(QueryRequest.class);
        then(queryClient).should().query(queryRequestArgumentCaptor.capture());

        final List<String> tagNames = queryRequestArgumentCaptor.getValue().getTags().get(0).getNames();
        assertThat(tagNames.size(), is(2));
        assertThat(tagNames.contains("rpm"), is(true));
        assertThat(tagNames.contains("actualTemperature"), is(true));
    }

    @Test
    public void queriesTimeSeriesWithAssetIds() {
        queryService.findPerformanceOverTime("foo", new Date(0L), new Date(123L));

        final ArgumentCaptor<QueryRequest> queryRequestArgumentCaptor = ArgumentCaptor.forClass(QueryRequest.class);
        then(queryClient).should().query(queryRequestArgumentCaptor.capture());

        final QueryRequest.Tag tag = queryRequestArgumentCaptor.getValue().getTags().get(0);
        assertThat(tag.getFilter().getAttributes().get("SteamTurbineId"), is("foo"));
    }

    @Test
    public void queriesTimeSeriesWithLimit() {
        queryService.findPerformanceOverTime("foo", new Date(0L), new Date(123L));

        final ArgumentCaptor<QueryRequest> queryRequestArgumentCaptor = ArgumentCaptor.forClass(QueryRequest.class);
        then(queryClient).should().query(queryRequestArgumentCaptor.capture());

        final QueryRequest.Tag tag = queryRequestArgumentCaptor.getValue().getTags().get(0);
        assertThat(tag.getLimit(), is(1000L));
    }

}
