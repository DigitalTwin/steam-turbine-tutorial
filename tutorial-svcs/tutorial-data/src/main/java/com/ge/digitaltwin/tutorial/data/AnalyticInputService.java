package com.ge.digitaltwin.tutorial.data;

import com.ge.digitaltwin.tutorial.common.coefficient.ModelCoefficient;
import com.ge.digitaltwin.tutorial.data.AnalyticInput.PerformanceAtTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AnalyticInputService {

    private final CoefficientService coefficientService;

    @Autowired
    public AnalyticInputService(CoefficientService coefficientService) {
        this.coefficientService = coefficientService;
    }

    public AnalyticInput convertPerformanceOverTimeToAnalyticInput(String assetId, Map<Date, Performance> performanceOverTime) {
        final AnalyticInput analyticInput = new AnalyticInput();

        performanceOverTime.entrySet().stream().forEachOrdered(datePerformanceEntry -> analyticInput.getSeries().add(
                new PerformanceAtTime(
                        datePerformanceEntry.getValue().getRpm(),
                        datePerformanceEntry.getValue().getActualTemperature(),
                        datePerformanceEntry.getKey().getTime())));

        final ModelCoefficient modelCoefficient = coefficientService.fetchCoefficientForAssetId(assetId);
        analyticInput.setSlope(modelCoefficient.getM());
        analyticInput.setIntercept(modelCoefficient.getB());

        return analyticInput;
    }
}
