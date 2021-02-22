package com.auth.config;


import com.auth.domain.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name="flames-service",url="${flames.services.employee-service}")
public interface EmployeeFiegnClient {

    @GetMapping(path="/employees/findbyid/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<EmployeeDTO> findById(@PathVariable Integer id);
}
