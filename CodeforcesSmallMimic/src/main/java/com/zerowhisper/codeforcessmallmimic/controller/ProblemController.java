package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.ProblemDto;
import com.zerowhisper.codeforcessmallmimic.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("/show/all")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(problemService.list());
    }

    @GetMapping("/show/{problemId}")
    public ResponseEntity<?> show(@PathVariable Long problemId) {
        try {
            return ResponseEntity.ok(problemService.getProblemById(problemId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProblemDto problem, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(problemService.add(problem, token), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{problemId}")
    public ResponseEntity<?> update(@PathVariable Long problemId, @RequestBody ProblemDto problem) {
        problemService.updateProblemById(problemId, problem);
        return ResponseEntity.ok(
                problemService.getProblemById(problemId));
    }

    @DeleteMapping("/delete/{problemId}")
    public ResponseEntity<?> delete(@PathVariable Long problemId) {
        problemService.deleteProblemById(problemId);
        return ResponseEntity.ok("Deleted problem "
                + problemId
                + " successfully."
        );
    }
}