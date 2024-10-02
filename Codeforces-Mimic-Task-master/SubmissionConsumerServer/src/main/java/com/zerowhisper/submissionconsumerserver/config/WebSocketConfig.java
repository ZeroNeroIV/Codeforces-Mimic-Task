package com.zerowhisper.submissionconsumerserver.config;

import com.zerowhisper.submissionconsumerserver.service.WebSocketMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket  // This enables WebSocket support
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketMessageHandler(), "/ws")
                .setAllowedOrigins("*");  // Adjust CORS settings if necessary
    }
}
