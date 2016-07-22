package com.ge.digitaltwin.tutorial.data;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AnalyticInput {

    private List<PerformanceAtTime> series;
    private double slope;
    private double intercept;

    public AnalyticInput() {
        series = new ArrayList<>();
    }

    public List<PerformanceAtTime> getSeries() {
        return series;
    }

    public void setSeries(List<PerformanceAtTime> series) {
        this.series = series;
    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getIntercept() {
        return intercept;
    }

    public void setIntercept(double intercept) {
        this.intercept = intercept;
    }

    static class PerformanceAtTime extends Performance {

        private long timestamp;

        public PerformanceAtTime(double rpm, double temperature, long timestamp) {
            super(rpm, temperature);
            this.timestamp = timestamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

}
