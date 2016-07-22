package com.ge.digitaltwin.tutorial.util;

import com.ge.dt.ptsc.PredixTimeSeriesClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(PredixTimeSeriesClientConfiguration.class)
public class TutorialTimeseriesUtilApplication {
    public static void main(String args[]) {
        SpringApplication.run(TutorialTimeseriesUtilApplication.class, args);
    }
}
