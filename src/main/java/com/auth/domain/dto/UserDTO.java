package com.auth.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class UserDTO {
    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Please enter your full name")
    private String fullname;
    //@JsonIgnore
    @NotBlank(message = "Password field is required")
    private String password;
    //@JsonIgnore
    @Transient
    private String confirmPassword;
   private Integer employeeId;

}
