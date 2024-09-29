<<<<<<< HEAD
package com.zerowhisper.codeforcessmallmimic.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaSubmissionProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.submission.name}")
    private String topicName;

    public void sendSubmission(@NotNull Map<String, Object> message) {
        kafkaTemplate.send(topicName, message);
    }

}
=======
package com.zerowhisper.codeforcessmallmimic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaSubmissionProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.submission.name}")
    private String topicName;

    public void sendSubmission(Map<String, Object> message) {
        kafkaTemplate.send(topicName, message);
    }

}
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
