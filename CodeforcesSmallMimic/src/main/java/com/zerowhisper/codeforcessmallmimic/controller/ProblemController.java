<<<<<<< HEAD
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
=======
package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.ProblemDto;
import com.zerowhisper.codeforcessmallmimic.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProblemDto problem) {
        try {
            return new ResponseEntity<>(problemService.add(problem), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Only admins can update problems

    @PutMapping("/update/{problemId}")
    public ResponseEntity<?> update(@PathVariable Long problemId, @RequestBody ProblemDto problem) {
        try {
            problemService.updateProblemById(problemId, problem);
            return ResponseEntity.ok(Map
                    .of("updated_problem_id",
                            problemId,
                            " successfully.\n",
                            problemService.getProblemById(problemId)
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Only admins can delete problems

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
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
