package com.auth.domain.dto;

import lombok.Data;


@Data
public class EmployeeDTO {
    private Integer id;
    private String surname;
    private String firstName;
    private String gender;
    private String email;
}
