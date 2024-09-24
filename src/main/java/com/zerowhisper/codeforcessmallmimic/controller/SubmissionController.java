package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.SubmissionDto;
import com.zerowhisper.codeforcessmallmimic.service.SubmissionService;
import lombok.AllArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    // I used void because we wont send anything yet to the user
    public ResponseEntity<?> submitCode(@RequestBody SubmissionDto submissionDto) {
        try {
            return ResponseEntity
                    .ok(submissionService.submitCode(submissionDto.getUserId(), submissionDto.getProblemId(),
                            submissionDto.getCode(),
                            submissionDto.getLanguageId()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
