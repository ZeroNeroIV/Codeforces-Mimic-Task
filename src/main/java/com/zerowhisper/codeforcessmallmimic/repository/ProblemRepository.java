package com.zerowhisper.codeforcessmallmimic.repository;

import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
