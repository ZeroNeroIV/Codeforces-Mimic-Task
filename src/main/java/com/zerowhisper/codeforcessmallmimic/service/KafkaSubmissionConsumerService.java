package com.zerowhisper.codeforcessmallmimic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaSubmissionConsumerService {
    private final ObjectMapper objectMapper;
    private final SubmissionService submissionService;

    @KafkaListener(topics = "submission-topic", groupId = "submission-group")
    public void listen(Map<String, Object> message) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI
                            .create("https://judge0-ce.p.rapidapi.com/submissions/" +
                                    message.get("token") +
                                    "?base64_encoded=true&fields=*")
                    )
                    .header("x-rapidapi-key", "be259d5389mshdf767541cdc8812p1c7839jsnafdc65c31663")
                    .header("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            client.close();
            processJudge0Response((Long) message.get("submissionId"), response.body());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void processJudge0Response(Long submissionId, String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            String statusDescription = rootNode.path("status").path("description").asText();
            String output = rootNode.path("stdout").asText(null);
            Integer executionTime = rootNode.path("time").asInt();
            Integer memory = rootNode.path("memory").asInt();

            Submission submission = submissionService
                    .updateSubmission(submissionId,
                            statusDescription,
                            output,
                            memory,
                            executionTime);

            notifyUser(submission);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // TODO: Add logic to notify the user
    private void notifyUser(Submission submission) {
        // Example of how you might notify the user
        System.out.println("Notifying user about the result of submission " + submission.getSubmissionId());
        // You could use WebSocket, email, or another service to notify the user
    }
}
