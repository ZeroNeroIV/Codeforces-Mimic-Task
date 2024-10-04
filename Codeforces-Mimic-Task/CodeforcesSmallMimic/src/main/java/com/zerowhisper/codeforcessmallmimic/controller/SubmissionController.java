package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.SubmissionDto;
import com.zerowhisper.codeforcessmallmimic.service.SubmissionService;
import com.zerowhisper.codeforcessmallmimic.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final UserAccountService userAccountService;

    @PostMapping
    public ResponseEntity<?> submitCode(@RequestBody SubmissionDto submissionDto) throws Exception {
        return ResponseEntity
                .ok(submissionService.submitCode(submissionDto));
    }

    @GetMapping("/get-submission/{submissionId}")
    public ResponseEntity<?> getSubmission(@PathVariable Long submissionId) {
        return ResponseEntity
                .ok(submissionService.getSubmission(submissionId));
    }

    @GetMapping("/get-submission")
    public ResponseEntity<?> getAllSubmission() {
        return ResponseEntity
                .ok(submissionService.getAllSubmission());
    }

    @GetMapping("/get-user-submissions/{userId}")
    public ResponseEntity<?> getUserSubmissions(@PathVariable Long userId) {
        return ResponseEntity
                .ok(userAccountService.getUserSubmissions(userId));
    }

}
