package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.service.SubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    // 9275cc2d-c94b-43a3-853c-962cbf15234e

    @GetMapping("/get/{submissionId}")
    public ResponseEntity<String> getSubmission(@PathVariable String submissionId) {
        try {
            return ResponseEntity.ok(submissionService.getSubmission(Long.valueOf(submissionId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
