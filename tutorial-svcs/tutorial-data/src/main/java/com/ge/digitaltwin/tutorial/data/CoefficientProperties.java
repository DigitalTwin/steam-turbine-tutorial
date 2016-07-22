package com.ge.digitaltwin.tutorial.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@ConfigurationProperties("com.ge.digitaltwin.tutorial.data.coefficient")
@SuppressWarnings("unused")
public class CoefficientProperties {

    private URI coefficientService;

    public URI getCoefficientService() {
        return coefficientService;
    }

    public void setCoefficientService(URI coefficientService) {
        this.coefficientService = coefficientService;
    }
}
