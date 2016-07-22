package com.ge.digitaltwin.tutorial.common;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@SuppressWarnings("unused")
public class AnalyticResult implements Serializable {

    private static final long serialVersionUID = -6245334597999387571L;

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private double expectedTemperature;

    @NotNull
    private double actualTemperature;

    @NotNull
    @Column(columnDefinition = "timestamp with time zone")
    private Date timestamp;

    @NotNull
    @Size(min = 1)
    private String assetId;

    @NotNull
    private int rpm;

    public AnalyticResult() {
        // Empty constructor needed in AnalyticResultTest
    }

    public AnalyticResult(double expectedTemperature, double actualTemperature, Date timestamp, String assetId, int rpm) {
        this.expectedTemperature = expectedTemperature;
        this.actualTemperature = actualTemperature;
        this.timestamp = timestamp;
        this.assetId = assetId;
        this.rpm = rpm;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getExpectedTemperature() {
        return expectedTemperature;
    }

    public void setExpectedTemperature(double expectedTemperature) {
        this.expectedTemperature = expectedTemperature;
    }

    public double getActualTemperature() {
        return actualTemperature;
    }

    public void setActualTemperature(double actualTemperature) {
        this.actualTemperature = actualTemperature;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    @Override
    @IgnoreCoverage
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalyticResult that = (AnalyticResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    @IgnoreCoverage
    public int hashCode() {
        return Objects.hash(id);
    }

    @Transient
    public double getTemperatureDelta() {
        return actualTemperature - expectedTemperature;
    }
}
