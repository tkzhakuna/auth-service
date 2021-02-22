package com.auth.controller;

import com.auth.domain.Role;
import com.auth.domain.dto.UserDTO;
import com.auth.service.RoleService;
import com.auth.service.impl.MapValidationErrorService;
import com.auth.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;
    private final MapValidationErrorService mapValidationErrorService;
    private final ResponseBuilder<Role> responseBuilder;

    public RoleController(RoleService roleService,MapValidationErrorService mapValidationErrorService,
                          ResponseBuilder<Role> responseBuilder) {
        this.roleService = roleService;
        this.mapValidationErrorService=mapValidationErrorService;
        this.responseBuilder=responseBuilder;
    }

    @PostMapping(path="/add-new",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> saveRole(@Valid @RequestBody Role role, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)
            return errorMap;

        return new ResponseEntity<>(responseBuilder.successResponse.apply(roleService.addNew(role)), HttpStatus.OK);

    }

    @PostMapping(path="/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Role role, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)
            return errorMap;

        return new ResponseEntity<>(responseBuilder.successResponse.apply(roleService.update(id,role)), HttpStatus.OK);

    }

    @GetMapping(path="/findbyid/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return new ResponseEntity<>(responseBuilder.successResponse.apply(roleService.findById(id)), HttpStatus.OK);

    }
    @GetMapping(path="/findall", produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);

    }
}
