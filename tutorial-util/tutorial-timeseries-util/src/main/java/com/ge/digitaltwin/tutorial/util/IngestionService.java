package com.ge.digitaltwin.tutorial.util;

import com.ge.dt.ptsc.IngestionRequest;
import com.ge.dt.ptsc.IngestionClient;
import com.ge.dt.ptsc.PredixTimeSeriesClientException;
import com.ge.dt.ptsc.IngestionSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngestionService {
    private static final String ATTRIBUTE_NAME_FOR_STEAM_TURBINE_ID = "SteamTurbineId";

    private static final int HIGH_QUALITY = 3;
    private static final int CHUNK_SIZE = 100;
    private static final String TAG_NAME_FOR_ACTUAL_TEMPERATURE = "actualTemperature";
    private static final String TAG_NAME_FOR_RPM = "rpm";

    private static final Logger LOG = LoggerFactory.getLogger(IngestionService.class);

    private final IngestionClient predixTimeSeriesClient;

    @Autowired
    public IngestionService(IngestionClient predixTimeSeriesClient) {
        this.predixTimeSeriesClient = predixTimeSeriesClient;
    }

    public void ingest(List<SteamTurbineDataPoint> turbineList) {
        try {
            predixTimeSeriesClient.doInSession(predixTimeseries -> processTurbineList(turbineList, predixTimeseries));
        } catch (PredixTimeSeriesClientException e) {
            LOG.error("Problem encountered while pushing steam turbines to Timeseries service instance", e);
        }
    }

    public Integer processTurbineList(List<SteamTurbineDataPoint> turbineList, IngestionSession predixTimeseries) {
        Integer turbineCount = 0;
        Integer pushCount = 0;
        IngestionRequest ingestionRequest = new IngestionRequest();
        for (SteamTurbineDataPoint turbine : turbineList) {
            turbineCount++;

            addTagAndValueToBody(ingestionRequest, turbine, TAG_NAME_FOR_ACTUAL_TEMPERATURE, (Double) turbine.getActualTemperature());
            addTagAndValueToBody(ingestionRequest, turbine, TAG_NAME_FOR_RPM, (Double) turbine.getRpm());

            if (turbineCount % CHUNK_SIZE == 0) {
                LOG.debug("##### sending steam turbine data payloads: " + (1 + (pushCount * CHUNK_SIZE)) + "-" + ((1 + pushCount) * CHUNK_SIZE));
                try {
                    predixTimeseries.ingest(ingestionRequest);
                    ingestionRequest = new IngestionRequest();
                } catch (PredixTimeSeriesClientException e) {
                    LOG.error("Problems encountered while attempting to process SteamTurbineDataPoint ingestion request", e);
                }
                pushCount++;
            }
        }
        if (ingestionRequest.getBodies().size() > 0) {
            LOG.debug("##### sending steam turbine data payload: " + (1 + pushCount * CHUNK_SIZE));
            try {
                predixTimeseries.ingest(ingestionRequest);
            } catch (PredixTimeSeriesClientException e) {
                LOG.error("Problems encountered while attempting to process Turbine ingestion request", e);
            }
        }
        LOG.debug("##### Done, processed " + turbineCount + " steam turbines.");
        LOG.debug("##### This application may take significant time to complete and exit cleanly as queue processing time is proportional to the size of the data.");
        return turbineCount;
    }

    private void addTagAndValueToBody(IngestionRequest ingestionRequest, SteamTurbineDataPoint turbine, String tag, Double value) {
        ingestionRequest.addBody(tag).addDataPoint(turbine.getTimestamp(), value, HIGH_QUALITY).addAttribute(ATTRIBUTE_NAME_FOR_STEAM_TURBINE_ID, turbine.getSteamTurbineId());
    }


}
