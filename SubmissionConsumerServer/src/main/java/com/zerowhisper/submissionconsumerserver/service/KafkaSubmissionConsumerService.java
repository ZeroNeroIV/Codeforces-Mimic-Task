package com.zerowhisper.submissionconsumerserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerowhisper.submissionconsumerserver.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaSubmissionConsumerService {
    private final ObjectMapper objectMapper;
    private final SubmissionRepository submissionRepository;
    private final WebSocketMessageHandler webSocketMessageHandler;
    //! For websocket and kafka
//    private final SimpMessagingTemplate messagingTemplate;

    @Value("${judge0-api-key}")
    private String judge0ApiKey;


    @KafkaListener(topics = "${kafka.topic.submission.name}", groupId = "${kafka.group-id.submission.name}")
    public void listen(String message) {
        try {
            Map<?, ?> submissionDetails = objectMapper.readValue(message, Map.class);

            Long submissionId = Long.valueOf(submissionDetails.get("submissionId").toString());
            String token = submissionDetails.get("token").toString();

            AsyncHttpClient client = new DefaultAsyncHttpClient();
            String response = client.prepareGet("https://judge0-ce.p.rapidapi.com/submissions/" +
                            token +
                            "?base64_encoded=true&fields=*")
                    .setHeader("x-rapidapi-key", judge0ApiKey)
                    .setHeader("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                    .execute()
                    .toCompletableFuture()
                    .thenApply(Response::getResponseBody)
                    .exceptionally(throwable -> "Error: " + throwable.getMessage())
                    .join();
            client.close();

            processJudge0Response(submissionId, response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void processJudge0Response(Long submissionId, String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            String status = rootNode.path("status").path("description").asText();
            String output = rootNode.path("stdout").asText(null);
            Double executionTime = rootNode.path("time").asDouble();
            Double memory = rootNode.path("memory").asDouble();

            submissionRepository.updateSubmissionStatus(submissionId,
                    status,
                    executionTime,
                    memory,
                    new String(Base64.getDecoder().decode(output)));

            notifyUser(submissionRepository.getUserAccountIdBySubmissionId(submissionId),
                    submissionRepository.getSubmissionById(submissionId));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // TODO: Add logic to notify the user
    private void notifyUser(String userAccountId, Map<String, Object> submission) {
        // You could use WebSocket to notify the user
        webSocketMessageHandler.sendMessageToClient(
                userAccountId,
                submission);
    }
}
