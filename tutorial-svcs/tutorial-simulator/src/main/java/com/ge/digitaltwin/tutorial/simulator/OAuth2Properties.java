package com.ge.digitaltwin.tutorial.simulator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URI;

@Component
@ConfigurationProperties("tutorial.simulator.oauth2")
public class OAuth2Properties {

    @NotNull
    @Size(min = 1)
    private String clientId;

    @NotNull
    @Size(min = 1)
    private String clientSecret;

    @NotNull
    private URI accessTokenUri;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public URI getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(URI accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

}
