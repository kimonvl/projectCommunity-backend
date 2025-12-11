package com.example.projectCommunity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class responsible for enabling and customizing the application's
 * Websocket messaging system using STOMP over SockJS.
 *
 * <p>This setup enables real-time client-server communication used for features
 * such as notifications and chat.</p>
 * */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers STOMP endpoints and fallback SockJS support.
     *
     * @param registry the registry used to configure Websocket endpoints.
     * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS();
    }

    /**
     * Configures message broker routing for chat and notifications real time system.
     *
     * @param registry the broker registry for configuring endpoint destinations.
     * */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
