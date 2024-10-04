package com.zerowhisper.codeforcessmallmimic.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSubmissionProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.submission.name}")
    private String topicName;

    public void sendSubmission(@NotNull String message) {
        kafkaTemplate.send(topicName, message);
    }

}