package com.ge.digitaltwin.tutorial.util;

import com.ge.dt.tsc.DigitalTwinTimeSeriesClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DigitalTwinTimeSeriesClientConfiguration.class)
public class TutorialTimeseriesUtilApplication {
    public static void main(String args[]) {
        SpringApplication.run(TutorialTimeseriesUtilApplication.class, args);
    }
}
