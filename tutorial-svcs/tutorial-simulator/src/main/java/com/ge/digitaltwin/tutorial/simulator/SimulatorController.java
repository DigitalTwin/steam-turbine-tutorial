package com.ge.digitaltwin.tutorial.simulator;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping(path = "/simulate")
public class SimulatorController {

    private final SimulatorService service;

    @Autowired
    public SimulatorController(SimulatorService service) {
        this.service = service;
    }

    @RequestMapping(method = POST)
    public void runSimulation(@RequestParam("assetId") String assetId, @RequestParam("startTime") Long startTime, @RequestParam("endTime") Long endTime, @RequestParam("intervalMilliseconds") Integer intervalMilliseconds) {
        service.runSimulation(assetId, startTime, endTime, intervalMilliseconds);
    }

}
