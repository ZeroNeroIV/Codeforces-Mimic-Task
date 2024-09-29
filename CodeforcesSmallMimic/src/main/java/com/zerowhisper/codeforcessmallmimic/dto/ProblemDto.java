<<<<<<< HEAD
package com.zerowhisper.codeforcessmallmimic.dto;

import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import lombok.Data;

@Data
public class ProblemDto {
    // DONE
    private String title;
    private String description;
    private String testCases;
    private String expectedOutput;
    private Double memoryLimit;
    private Double timeLimit;
}
=======
package com.zerowhisper.codeforcessmallmimic.dto;

import lombok.Data;

@Data
public class ProblemDto {
    // DONE

    private String title;
    private String description;
    private String testCases;
    private String expectedOutput;
    private Integer memoryLimit;
    private Integer timeLimit;
}
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
