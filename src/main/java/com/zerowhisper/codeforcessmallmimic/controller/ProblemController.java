package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.ProblemDto;
import com.zerowhisper.codeforcessmallmimic.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private final ProblemService problemService;

    // TODO: make it available for all

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(problemService.list());
    }

    @GetMapping("/show-problem/{problemId}")
    public ResponseEntity<?> show(@PathVariable Long problemId) {
        try {
            return ResponseEntity.ok(problemService.getProblemById(problemId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Only admins can add problems
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProblemDto problem) {
        try {
            return ResponseEntity.ok("Created Successfully\n" + problemService.add(problem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Only admins can update problems
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{problemId}")
    public ResponseEntity<?> update(@PathVariable Long problemId, @RequestBody ProblemDto problem) {
        try {
            problemService.updateProblemById(problemId, problem);
            return ResponseEntity.ok("Updated problem "
                    + problemId
                    + " successfully.\n"
                    + problemService.getProblemById(problemId)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Only admins can delete problems
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{problemId}")
    public ResponseEntity<?> delete(@PathVariable Long problemId) {
        try {
            problemService.deleteProblemById(problemId);
            return ResponseEntity.ok("Deleted problem "
                    + problemId
                    + " successfully."
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
