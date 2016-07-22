package com.ge.digitaltwin.tutorial.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@CrossOrigin
@RequestMapping("/input")
public class AnalyticInputController {

    private final QueryService queryService;
    private final AnalyticInputService analyticInputService;

    @Autowired
    public AnalyticInputController(QueryService queryService, AnalyticInputService analyticInputService) {
        this.queryService = queryService;
        this.analyticInputService = analyticInputService;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public AnalyticInput getAnalyticInputForPerformanceOverTime(@RequestParam String assetId, @RequestParam Date startTimestamp, @RequestParam Date endTimestamp) {
        return analyticInputService.convertPerformanceOverTimeToAnalyticInput(
                assetId, queryService.findPerformanceOverTime(assetId, startTimestamp, endTimestamp));
    }
}
