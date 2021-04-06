package com.auth.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class UserDTO {
    @Email(message = "Username must be an email")
    @NotNull @NotBlank(message = "username is required")
    @Column(unique = true,nullable = false)
    private String username;
   @NotNull @NotBlank(message = "Please enter your full name")
   @Column(nullable = false)
    private String fullname;
    //@JsonIgnore
    @NotNull @NotBlank(message = "Password field is required")
    @Column(nullable = false)
    private String password;
    //@JsonIgnore
    @Transient
    private String confirmPassword;
    //private Integer employeeId;

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +

                '}';
    }
}
