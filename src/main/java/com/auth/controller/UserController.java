package com.auth.controller;



import com.auth.domain.Role;
import com.auth.domain.User;

import com.auth.domain.dto.UserDTO;
import com.auth.payload.JWTLoginSucessReponse;
import com.auth.payload.LoginRequest;
import com.auth.payload.Response;
import com.auth.security.JwtTokenProvider;
import com.auth.service.UserService;
import com.auth.service.impl.CustomUserDetailsService;
import com.auth.service.impl.MapValidationErrorService;
import com.auth.util.ResponseBuilder;
import com.auth.validator.UserValidator;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import static com.auth.security.SecurityConstants.TOKEN_PREFIX;

@Slf4j
@Data
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final MapValidationErrorService mapValidationErrorService;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserValidator userValidator;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ResponseBuilder<User> userResponseBuilder;
    private final ResponseBuilder<UserDTO> responseBuilder;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User user = userService.findByUsername(loginRequest.getUsername());

        if (user != null) {
            userService.checkRoles(user);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

    @PostMapping(path = "/register-user", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response> registerUser(@Valid @RequestBody UserDTO user, BindingResult result) {
        log.info("Request to add user");
        // Validate passwords match
        userValidator.validate(user, result);

//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//        if (errorMap != null) return errorMap;

        UserDTO newUser = userService.saveUser(user);

        return new ResponseEntity<>(responseBuilder.successResponse.apply(newUser,null), HttpStatus.CREATED);
    }

    @PatchMapping(path="/update-user/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
   // @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER')")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User updatedUser = userService.updateUser(id, user);

        return  ResponseEntity.ok(userResponseBuilder.successResponse.apply(updatedUser,null));
    }

    @GetMapping("/all-users")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER')")
    public ResponseEntity<Response> findAll() {

        return  ResponseEntity.ok(userResponseBuilder.successResponse.apply(null,userService.findAll()));
    }

    @GetMapping("/find-by-id/{id}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER')")
    public ResponseEntity<Response> findById(@PathVariable Integer id) {
        return  ResponseEntity.ok(userResponseBuilder.successResponse.apply(userService.findById(id),null));
    }


    @GetMapping("/roles")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER')")
    public List<Role> findRoles() {
        return userService.findRoles();
    }



}
