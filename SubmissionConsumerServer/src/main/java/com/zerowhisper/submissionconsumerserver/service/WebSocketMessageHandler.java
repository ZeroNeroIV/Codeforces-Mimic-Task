package com.zerowhisper.submissionconsumerserver.service;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@NonNullApi
public class WebSocketMessageHandler extends TextWebSocketHandler {

    // Map to associate client IDs with WebSocket sessions
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Extract client ID from the WebSocket URL or headers
        String clientId = getClientIdFromSession(session);

        // Store the session associated with the client ID
        sessions.put(clientId, session);

        System.out.println("Client connected: " + clientId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Remove the session when the connection is closed
        String clientId = getClientIdFromSession(session);
        sessions.remove(clientId);

        System.out.println("Client disconnected: " + clientId);
        sendMessageToClient(clientId, "Welcome");
    }

    // Method to send a message to a specific client by client ID
    public <T> void sendMessageToClient(String clientId, T message) {
        WebSocketSession session = sessions.get(clientId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message.toString()));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Client not connected: " + clientId);
        }
    }

    // Helper method to extract the client ID (from query params or headers)
    private String getClientIdFromSession(WebSocketSession session) {
        // For example, extract client ID from WebSocket URI query parameters
        // ws://localhost:XXXX/ws?userAccountId=12345
        String query = Objects.requireNonNull(session.getUri()).getQuery();
        return query.split("=")[1]; // Assuming query is like ?clientId=123
    }
}