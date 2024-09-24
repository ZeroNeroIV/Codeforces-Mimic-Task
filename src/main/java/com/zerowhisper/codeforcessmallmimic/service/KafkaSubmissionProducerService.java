package com.zerowhisper.codeforcessmallmimic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaSubmissionProducerService {
    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    @Value("${kafka.topic.submission.name}")
    private String topicName;

    public void sendSubmission(Map<String, Object> message) {
        kafkaTemplate.send(topicName, message);
    }
}
