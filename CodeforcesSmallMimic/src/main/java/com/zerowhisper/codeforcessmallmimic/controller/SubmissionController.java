<<<<<<< HEAD
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

    @GetMapping("/get-user-submissions/{userId}")
    public ResponseEntity<?> getUserSubmissions(@PathVariable Long userId) {
        return ResponseEntity
                .ok(userAccountService.getUserSubmissions(userId));
    }

}
=======
package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.SubmissionDto;
import com.zerowhisper.codeforcessmallmimic.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<?> submitCode(@RequestBody SubmissionDto submissionDto) {
        try {
            return ResponseEntity
                    .ok(submissionService.submitCode(
                            submissionDto.getUserId(),
                            submissionDto.getProblemId(),
                            submissionDto.getCode(),
                            submissionDto.getLanguageId())
                    );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/get-submission/{submissionId}")
    public ResponseEntity<?> getSubmission(@PathVariable Long submissionId) {
        try {
            return ResponseEntity
                    .ok(submissionService.getSubmission(submissionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
