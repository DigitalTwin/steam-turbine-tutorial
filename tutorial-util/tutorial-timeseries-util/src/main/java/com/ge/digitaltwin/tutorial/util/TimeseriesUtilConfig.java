package com.ge.digitaltwin.tutorial.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "config")
public class TimeseriesUtilConfig {

    @NotNull
    private String steamTurbineId;

    @NotNull
    private String csvFilename;

    public String getSteamTurbineId() {
        return steamTurbineId;
    }

    public void setSteamTurbineId(String steamTurbineId) {
        this.steamTurbineId = steamTurbineId;
    }

    public String getCsvFilename() {
        return csvFilename;
    }

    public void setCsvFilename(String csvFilename) {
        this.csvFilename = csvFilename;
    }

    @Override
    public String toString() {
        return "TimeseriesUtilConfig{" +
                "steamTurbineId='" + steamTurbineId + '\'' +
                ", csvFilename='" + csvFilename + '\'' +
                '}';
    }
}
