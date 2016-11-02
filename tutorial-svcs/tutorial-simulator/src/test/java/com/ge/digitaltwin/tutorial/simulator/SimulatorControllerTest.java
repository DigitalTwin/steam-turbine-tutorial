package com.ge.digitaltwin.tutorial.simulator;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.mockito.Mock;

public class SimulatorControllerTest {

    private SimulatorController controller;

    @Mock
    private SimulatorService service;

    @Before
    public void setup() {
        initMocks(this);
        controller = new SimulatorController(service);
    }

    @Test
    @Ignore
    public void runsSimulation() {
        final String assetId = "asset123";
        final Long startTime = 1436234000L;
        final Integer intervalMilliseconds = 60000;
        final Long endTime = startTime + intervalMilliseconds*10;
        controller.runSimulation(assetId, startTime, endTime, intervalMilliseconds);
        then(service).should().runSimulation(eq(assetId), eq(startTime), eq(endTime), eq(intervalMilliseconds));
    }

}
