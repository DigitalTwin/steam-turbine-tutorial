package com.ge.digitaltwin.tutorial.result;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnalyticResultControllerTest {

    private AnalyticResultController analyticResultController;

    @Mock
    private AnalyticResultService analyticResultService;

    @Before
    public void setup() {
        initMocks(this);
        analyticResultController = new AnalyticResultController(analyticResultService);
    }

    @Test
    public void savesAnalyticResults() {
        final List<AnalyticResult> analyticResults = new ArrayList<>();
        analyticResultController.saveAnalyticResults(analyticResults);
        then(analyticResultService).should().saveAnalyticResults(same(analyticResults));
    }

    @Test
    public void notifiesClientsOfNewResults() {
        final List<AnalyticResult> analyticResults = new ArrayList<>();
        analyticResults.add(new AnalyticResult(0D, 0D, new Date(), "foo", 123));
        analyticResults.add(new AnalyticResult(0D, 0D, new Date(), "bar", 123));
        analyticResultController.saveAnalyticResults(analyticResults);
        then(analyticResultService).should().notifyClientsOfNewResults(eq(asList(new String[] {"foo", "bar"})));
    }

    @Test
    public void streamsAnalyticResultsToResponse() {
        final ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
        analyticResultController.streamAnalyticResultsToResponse("foo", responseOutputStream);
        then(analyticResultService).should().streamResultsForAssetId(eq("foo"), same(responseOutputStream));
    }
}
