<<<<<<<< HEAD:CodeforcesSmallMimic/src/main/java/com/zerowhisper/codeforcessmallmimic/dto/LogInRequestDto.java
package com.zerowhisper.codeforcessmallmimic.dto;


import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LogInRequestDto {
    @Email(message = "Invalid email address")
    private String email;
    private String password;
}
========
package com.zerowhisper.codeforcessmallmimic.dto;


import lombok.Data;

@Data
public class LogInDto {
    private String email;
    private String password;
}
>>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9:CodeforcesSmallMimic/src/main/java/com/zerowhisper/codeforcessmallmimic/dto/LogInDto.java
