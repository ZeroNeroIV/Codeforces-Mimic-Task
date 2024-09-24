package com.zerowhisper.codeforcessmallmimic.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.zerowhisper.codeforcessmallmimic.entity.Submission;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class Judge0_Service {

    // private final Submission submission;

    public String sendCodeToJudge0(Submission submission) throws IOException, InterruptedException {

        // Source code after encoded
        // input / output encoded

        String source_code = submission.getSourceCode();
        String stdin = submission.getProblem().getTestCaseInput();
        String expectedOutput = submission.getProblem().getTestCaseOutput();

        // encoded
        String encodedSourceCode = Base64.getEncoder().encodeToString(source_code.getBytes());
        String encodedStdin = Base64.getEncoder().encodeToString(stdin.getBytes());
        String encodedExpectedOutput = Base64.getEncoder().encodeToString(expectedOutput.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=true&wait=false&fields=*"))
                .header("x-rapidapi-key", "44554a7c0fmsh9cd1f71adacf0afp111683jsn9c255e35569d")// authenticate the user
                .header("x-rapidapi-host", "judge0-ce.p.rapidapi.com") // host
                .header("Content-Type", "application/json") // type
                .method("POST",
                        HttpRequest.BodyPublishers
                                .ofString("{\"language_id\":" + submission.getLanguageId() +
                                        ",\"source_code\":\"" + encodedSourceCode +
                                        "\",\"stdin\":\"" + encodedStdin +
                                        "\",\"expected_output\":\"" + encodedExpectedOutput +
                                        "\"}"))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response.body();
    }

}
