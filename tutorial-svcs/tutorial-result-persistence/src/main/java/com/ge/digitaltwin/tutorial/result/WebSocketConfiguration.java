package com.ge.digitaltwin.tutorial.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.StompBrokerRelayRegistration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private StompProperties stompProperties;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.setPathMatcher(new AntPathMatcher("."));

        final StompBrokerRelayRegistration stompBrokerRelayRegistration = registry.enableStompBrokerRelay("/topic");
        stompBrokerRelayRegistration.setRelayHost(stompProperties.getHost());
        stompBrokerRelayRegistration.setRelayPort(stompProperties.getPort());
        stompBrokerRelayRegistration.setVirtualHost(stompProperties.getVhost());
        stompBrokerRelayRegistration.setSystemLogin(stompProperties.getUsername());
        stompBrokerRelayRegistration.setSystemPasscode(stompProperties.getPassword());
        stompBrokerRelayRegistration.setClientLogin(stompProperties.getUsername());
        stompBrokerRelayRegistration.setClientPasscode(stompProperties.getPassword());
    }

}