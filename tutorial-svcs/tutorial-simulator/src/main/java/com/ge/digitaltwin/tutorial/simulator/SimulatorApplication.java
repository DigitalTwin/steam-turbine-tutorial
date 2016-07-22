package com.ge.digitaltwin.tutorial.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
    }

    @Bean
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails(OAuth2Properties oAuth2Properties) {
        final ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setAccessTokenUri(oAuth2Properties.getAccessTokenUri().toString());
        clientCredentialsResourceDetails.setClientId(oAuth2Properties.getClientId());
        clientCredentialsResourceDetails.setClientSecret(oAuth2Properties.getClientSecret());
        return clientCredentialsResourceDetails;
    }
}
