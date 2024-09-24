package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import com.zerowhisper.codeforcessmallmimic.repository.SubmissionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private static final String SUBMISSION_TOPIC = "submission_topic";

    private final SubmissionRepository submissionRepository;
    private final KafkaSubmissionProducerService kafkaSubmissionProducerService;
    private final RestTemplate restTemplate;

    /*
    private static final String SUBMISSION_URL = JUDGE0_URL + "?base64_encoded=false&wait=false";

    public void submitCode(Long userId, String sourceCode, String languageId) {
        // Step 1: Save the submission in the database without the token
        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setSourceCode(sourceCode);
        submission.setLanguageId(languageId);
        submission.setStatus("Pending");
        submissionRepository.save(submission);

        // Step 2: Send the code to Judge0 to get a token
        String judge0Url = "https://api.judge0.com/submissions/?base64_encoded=false&wait=false";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String body = String.format("{\"source_code\": \"%s\", \"language_id\": \"%s\"}", sourceCode, languageId);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(judge0Url, HttpMethod.POST, entity, String.class);

        // Step 3: Parse the response to extract the token
        String token = parseTokenFromResponse(response.getBody());

        // Step 4: Send the token to the Kafka topic
        Map<String, Object> message = new HashMap<>();
        message.put("token", token);
        message.put("submissionId", submission.getId());
        kafkaProducerService.sendMessage(SUBMISSION_TOPIC, message);

        // Submission is saved without the token, and the token is sent to Kafka.
    }

    private String parseTokenFromResponse(String responseBody) {
        // Assuming responseBody contains the token in a field "token"
        // Parse JSON response to extract the token (using libraries like Jackson or Gson)
        // Example with pseudo-code:
        // {
        //   "token": "your-judge0-token"
        // }
        String token = // Extract token from responseBody
        return token;
    }
     */

    public String getSubmission(@NotNull Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new RuntimeException("Submission with id " + submissionId + " does not exist."));

        if (submission.getSubmissionStatus().equals("In Queue")
                || submission.getSubmissionStatus().equals("Processing")) {
            // TODO: Check the token again.

            // TODO: If the token is still valid, check the status.

            // TODO: Otherwise, send the submission to Judge0.

            // TODO: Update the status in the database.
            submission.setSubmissionStatus("");
            submission.setTimeTaken(0);
            submission.setMemoryTaken(0);
            submission.setIsFinished(true);
            submissionRepository.save(submission);
        }
        return "submissionId: " +
                submissionId +
                "\nsource_code: " +
                submission.getSourceCode() +
                "\ntime taken: " +
                submission.getTimeTaken() +
                "\nmemory taken: " +
                submission.getMemoryTaken() +
                "\nstatus: " +
                submission.getSubmissionStatus();
    }

    public Submission updateSubmission(Long submissionId,
                                       String statusDescription,
                                       String output,
                                       Integer memory,
                                       Integer executionTime) {

        Submission submission = submissionRepository.getReferenceById(submissionId);
        submission.setSubmissionStatus(statusDescription);
        submission.setUserOutput(output);
        submission.setMemoryTaken(memory);
        submission.setTimeTaken(executionTime);
        submissionRepository.save(submission);
        return submission;
    }
}
