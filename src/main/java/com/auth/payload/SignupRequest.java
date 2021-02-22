package com.auth.payload;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 40)
    private String username;
    private Set<String> role;
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
    private String confirmPassword;

}
