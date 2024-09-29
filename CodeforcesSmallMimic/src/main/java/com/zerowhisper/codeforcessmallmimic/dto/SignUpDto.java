<<<<<<< HEAD
package com.zerowhisper.codeforcessmallmimic.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Data
public class SignUpDto {
    private String username;
    @Email(message = "Invalid email address")
    private String email;
    private String password;
    private List<String> roles;
}
=======
package com.zerowhisper.codeforcessmallmimic.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String username;
    private String email;
    private String password;
}
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
