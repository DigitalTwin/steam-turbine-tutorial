package com.ge.digitaltwin.tutorial.postprocessing;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/transform")
public class PostProcessingController {

    @RequestMapping(path = "/{assetId}", method = POST)
    public List<AnalyticResult> transformRawAnalyticOutput(@PathVariable String assetId, @RequestBody String rawAnalyticOutput) {
        return PostProcessingService.transformRawAnalyticOutput(assetId, rawAnalyticOutput);
    }
}
