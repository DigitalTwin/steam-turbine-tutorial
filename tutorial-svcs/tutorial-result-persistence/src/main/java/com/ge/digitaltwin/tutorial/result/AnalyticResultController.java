package com.ge.digitaltwin.tutorial.result;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/analyticResults")
public class AnalyticResultController {

    private AnalyticResultService analyticResultService;

    @Autowired
    public AnalyticResultController(AnalyticResultService analyticResultService) {
        this.analyticResultService = analyticResultService;
    }

    @RequestMapping(method = POST)
    public void saveAnalyticResults(@RequestBody List<AnalyticResult> analyticResults) {
        analyticResultService.saveAnalyticResults(analyticResults);
        analyticResultService.notifyClientsOfNewResults(
                analyticResults.stream().map(AnalyticResult::getAssetId).distinct().collect(toList()));
    }

    @RequestMapping(path = "/{assetId}", method = GET)
    public void streamAnalyticResultsToResponse(@PathVariable String assetId, OutputStream responseOutputStream) {
        analyticResultService.streamResultsForAssetId(assetId, responseOutputStream);
    }
}
