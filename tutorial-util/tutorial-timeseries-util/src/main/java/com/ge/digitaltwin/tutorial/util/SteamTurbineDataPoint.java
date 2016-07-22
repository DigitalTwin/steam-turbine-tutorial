package com.ge.digitaltwin.tutorial.util;

import java.util.Date;

public class SteamTurbineDataPoint {
    private String steamTurbineId;
    private Date timestamp;
    private Number actualTemperature;
    private Number rpm;


    public Number getActualTemperature() {
        return actualTemperature;
    }

    public void setActualTemperature(Number actualTemperature) {
        this.actualTemperature = actualTemperature;
    }

    public Number getRpm() {
        return rpm;
    }

    public void setRpm(Number rpm) {
        this.rpm = rpm;
    }

    public String getSteamTurbineId() {
        return steamTurbineId;
    }

    public void setSteamTurbineId(String steamTurbineId) {
        this.steamTurbineId = steamTurbineId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}
