package com.ge.digitaltwin.tutorial.data;

import com.ge.digitaltwin.tutorial.common.coefficient.ModelCoefficient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CoefficientService {

    private final CoefficientProperties coefficientProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public CoefficientService(CoefficientProperties coefficientProperties, RestTemplate restTemplate) {
        this.coefficientProperties = coefficientProperties;
        this.restTemplate = restTemplate;
    }

    public ModelCoefficient fetchCoefficientForAssetId(String assetId) {
        return restTemplate.getForObject(
                UriComponentsBuilder.fromUri(
                        coefficientProperties.getCoefficientService())
                        .queryParam("assetId", assetId).build().toUri(),
                ModelCoefficient.class);
    }
}
