package com.ge.digitaltwin.tutorial.result;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import com.mockrunner.mock.jdbc.MockResultSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ge.digitaltwin.tutorial.result.AnalyticResultService.ANALYTIC_RESULT_TOPIC;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;

public class AnalyticResultServiceTest {

    private AnalyticResultService analyticResultService;

    @Mock
    private AnalyticResultRepository analyticResultRepository;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        initMocks(this);
        analyticResultService = new AnalyticResultService(analyticResultRepository, simpMessagingTemplate, jdbcTemplate);
    }

    @Test
    public void savesAnalyticResults() {
        final List<AnalyticResult> analyticResults = new ArrayList<>();
        analyticResultService.saveAnalyticResults(analyticResults);
        then(analyticResultRepository).should().save(same(analyticResults));
    }

    @Test
    public void notifiesClientsOfNewResults() {
        final List<String> assetIds = asList("foo", "bar");
        analyticResultService.notifyClientsOfNewResults(assetIds);
        then(simpMessagingTemplate).should().convertAndSend(ANALYTIC_RESULT_TOPIC, "foo");
        then(simpMessagingTemplate).should().convertAndSend(ANALYTIC_RESULT_TOPIC, "bar");
    }

    @Test
    public void streamsResultsForAssetId() throws SQLException, IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final OutputStreamWriter resultWriter = new OutputStreamWriter(outputStream);
        invokeMethod(analyticResultService, "printResultsAsCsv", "foo", resultWriter);

        final ArgumentCaptor<RowCallbackHandler> rowCallbackHandlerArgumentCaptor = ArgumentCaptor.forClass(RowCallbackHandler.class);
        then(jdbcTemplate).should().query(anyString(), eq(new String[]{"foo"}), rowCallbackHandlerArgumentCaptor.capture());

        final MockResultSet resultSet = new MockResultSet("result");
        resultSet.addColumn("timestamp", asList(123L, 1234L));
        resultSet.addColumn("expected_temperature", asList(234D, 2345D));
        resultSet.addColumn("actual_temperature", asList(345D, 3456D));

        resultSet.next();
        rowCallbackHandlerArgumentCaptor.getValue().processRow(resultSet);

        resultSet.next();
        rowCallbackHandlerArgumentCaptor.getValue().processRow(resultSet);

        resultWriter.flush();
        assertThat(outputStream.toString(), is("123,234.0,345.0\r\n1234,2345.0,3456.0\r\n"));
    }

}
