package com.ge.digitaltwin.tutorial.common.coefficient;

import com.ge.digitaltwin.tutorial.common.IgnoreCoverage;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "assetId"))
@SuppressWarnings("unused")
public class ModelCoefficient implements Serializable {

    private static final long serialVersionUID = -7578278225621510985L;

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Size(min = 1)
    private String assetId;

    @NotNull
    private double m;

    @NotNull
    private double b;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    @Override
    @IgnoreCoverage
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ModelCoefficient that = (ModelCoefficient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    @IgnoreCoverage
    public int hashCode() {
        return Objects.hash(id);
    }
}
