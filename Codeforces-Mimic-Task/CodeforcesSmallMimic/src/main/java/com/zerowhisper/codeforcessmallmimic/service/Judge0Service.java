package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class Judge0Service {
    @Value("${judge0-api-key}")
    private String judge0ApiKey;

    // TODO: Add memory and time limits to the request
    public String sendCodeToJudge0(@NotNull Submission submission) throws IOException, InterruptedException {

        //? (Source code / Input / Output) Before Encoding
        String source_code =
                submission.getSourceCode();
        String stdin =
                submission.getProblem().getTestCaseInput();
        String expectedOutput =
                submission.getProblem().getTestCaseOutput();
        Double memoryLimit =
                submission.getProblem().getMemoryLimit();
        Double timeLimit =
                submission.getProblem().getTimeLimit();

        //? (Source code / Input / Output) After Encoding
        String encodedSourceCode =
                Base64.getEncoder().encodeToString(source_code.getBytes());
        String encodedStdin =
                Base64.getEncoder().encodeToString(stdin.getBytes());
        String encodedExpectedOutput =
                Base64.getEncoder().encodeToString(expectedOutput.getBytes());

        //? Building Request
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI
                        .create("https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=true&wait=false&fields=*"))
                .header("x-rapidapi-key",
                        judge0ApiKey) // authenticate the user (us)
                .header("x-rapidapi-host",
                        "judge0-ce.p.rapidapi.com") // host
                .header("Content-Type",
                        "application/json") // type
                .method("POST", HttpRequest
                        .BodyPublishers
                        .ofString("{\"language_id\":" + submission.getLanguageId() +
                                ",\"source_code\":\"" + encodedSourceCode +
                                "\",\"stdin\":\"" + encodedStdin +
                                "\",\"expected_output\":\"" + encodedExpectedOutput +
                                "\",\"memory_limit\":\"" + memoryLimit +
                                "\",\"cpu_time_limit\":\"" + timeLimit +
                                "\"}"))
                .build();

        //? Sending Request and getting response
        HttpClient client = HttpClient.newHttpClient();
        String token = client
                .send(request, HttpResponse
                        .BodyHandlers
                        .ofString()
                ).body()
                .replace("{\"token\":\"", "")
                .replace("\"}", "");
        client.close();

        return token;
    }

}