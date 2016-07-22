package com.ge.digitaltwin.tutorial.data;

@SuppressWarnings("unused")
public class Performance {

    private double rpm;
    private double actualTemperature;

    public Performance() {
        // Default constructor
    }

    public Performance(double rpm, double actualTemperature) {
        this.rpm = rpm;
        this.actualTemperature = actualTemperature;
    }

    public double getRpm() {
        return rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;
    }

    public double getActualTemperature() {
        return actualTemperature;
    }

    public void setActualTemperature(double actualTemperature) {
        this.actualTemperature = actualTemperature;
    }
}
