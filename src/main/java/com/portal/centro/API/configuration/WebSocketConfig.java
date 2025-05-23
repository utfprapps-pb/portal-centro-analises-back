package com.portal.centro.API.configuration;

import com.portal.centro.API.security.AuthHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss")
                .addInterceptors(new AuthHandshakeInterceptor())
                .setAllowedOriginPatterns("https://ca-dev.app.pb.utfpr.edu.br")
                .withSockJS();

        registry.addEndpoint("/ws")
                .addInterceptors(new AuthHandshakeInterceptor())
                .setAllowedOriginPatterns("http://localhost:4200")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // para onde as mensagens serão enviadas
        config.setApplicationDestinationPrefixes("/app"); // prefixo das mensagens enviadas do frontend
    }
}
