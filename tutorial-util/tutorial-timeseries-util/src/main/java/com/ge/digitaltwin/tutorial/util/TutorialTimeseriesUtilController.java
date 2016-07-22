package com.ge.digitaltwin.tutorial.util;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class TutorialTimeseriesUtilController implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TutorialTimeseriesUtilController.class);

    private final IngestionService ingestionService;

    private final TimeseriesUtilConfig timeseriesUtilConfig;

    @Autowired
    public TutorialTimeseriesUtilController(IngestionService ingestionService, TimeseriesUtilConfig timeseriesUtilConfig) {
        this.ingestionService = ingestionService;
        this.timeseriesUtilConfig = timeseriesUtilConfig;
    }

    @Override
    public void run(String... args) throws Exception {

        LOG.debug("##### Configuration: " + timeseriesUtilConfig);
        LOG.debug("##### TutorialTimeseriesUtilController: up and running...");
        LOG.debug("##### IngestionService instance: " + this.ingestionService);

        List<SteamTurbineDataPoint> turbineList = getSteamTurbineDataPoints(timeseriesUtilConfig.getCsvFilename(), timeseriesUtilConfig.getSteamTurbineId());
        LOG.debug("##### Found " + turbineList.size()  +" SteamTurbineDataPoints in " + timeseriesUtilConfig.getCsvFilename() + " for steamTurbineId=" + timeseriesUtilConfig.getSteamTurbineId());
        ingestionService.ingest(turbineList);
    }

    private static List<SteamTurbineDataPoint> getSteamTurbineDataPoints(String csvFilename, String steamTurbineId) throws IOException {
        final List<SteamTurbineDataPoint> steamTurbineDataPointList = new ArrayList<>();
        final ClassLoader classLoader = TutorialTimeseriesUtilController.class.getClassLoader();
        File csvFile;
        if (classLoader.getResource(csvFilename) != null) {
            LOG.debug("##### found resource: " + csvFilename);
            csvFile = new File(classLoader.getResource(csvFilename).getFile());
        } else {
            LOG.debug("##### resource " + csvFilename + " not found, looking for file...");
            csvFile = new File(csvFilename);
            if (csvFile == null) {
                LOG.debug("##### file " + csvFilename + " not found");
            } else {
                LOG.debug("##### found file: " + csvFilename);
            }
        }

        final CSVReader steamTurbineCSVReader = new CSVReader(new FileReader(csvFile));
        final Iterator<String[]> steamTurbineIterator = steamTurbineCSVReader.iterator();

        if (steamTurbineIterator.hasNext()) { steamTurbineIterator.next(); } // skip over header row

        while (steamTurbineIterator.hasNext()) {
            String[] steamTurbineRecord = steamTurbineIterator.next();
            SteamTurbineDataPoint steamTurbineDataPoint = new SteamTurbineDataPoint();
            steamTurbineDataPoint.setSteamTurbineId(steamTurbineId);
            steamTurbineDataPoint.setTimestamp(new Date(new Long(steamTurbineRecord[0])));
            steamTurbineDataPoint.setRpm(new Double(steamTurbineRecord[1]));
            steamTurbineDataPoint.setActualTemperature(new Double(steamTurbineRecord[2]));
            steamTurbineDataPointList.add(steamTurbineDataPoint);
        }
        steamTurbineCSVReader.close();

        return steamTurbineDataPointList;
    }

}
