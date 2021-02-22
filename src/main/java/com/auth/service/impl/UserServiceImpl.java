package com.auth.service.impl;


import com.auth.config.EmployeeFiegnClient;
import com.auth.domain.Role;
import com.auth.domain.User;

import com.auth.domain.dto.EmployeeDTO;
import com.auth.domain.dto.UserDTO;
import com.auth.exceptions.InvalidParameterException;
import com.auth.exceptions.NotFoundException;
import com.auth.exceptions.UnexpectedException;
import com.auth.exceptions.UsernameAlreadyExistsException;

import com.auth.repository.RoleRepository;
import com.auth.repository.UserRepository;
import com.auth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final RoleRepository roleRepository;
    private final EmployeeFiegnClient employeeFiegnClient;//implement feign

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager,
                           RoleRepository roleRepository, EmployeeFiegnClient employeeFiegnClient) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.roleRepository = roleRepository;
        this.employeeFiegnClient = employeeFiegnClient;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        try {
            if (userRepository.findByUsername(userDTO.getUsername()) != null) {
                throw new UsernameAlreadyExistsException("Username '" + userDTO.getUsername() + "' already exists");
            } else {
                BeanUtils.copyProperties(userDTO, user);
            }
        } catch (UsernameAlreadyExistsException uae) {
            throw new RuntimeException("User already exists");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


        EmployeeDTO employee = null;
        if (!Objects.isNull(userDTO.getEmployeeId()) && userDTO.getEmployeeId() > 0) {
            try {
                //check employee
                employee = employeeFiegnClient.findById(userDTO.getEmployeeId()).getBody();
            } catch (Exception ex) {
                throw new UnexpectedException("Employee details not found, Please make sure user is registered in the employee details");
            }

            if (Objects.isNull(employee)) {
                throw new NotFoundException("Employee not found ");
            }

            //check if emails match
            if (employee.getEmail()==null||!employee.getEmail().equals(userDTO.getUsername())) {
                throw new NotFoundException("Employee with provided email not found");
            }
        } else {
            throw new InvalidParameterException("Invalid Employee Id supplied");
        }

        if (employee != null) {
            //Add Employee
            user.setEmployeeId(employee.getId());//Fix to dynamically add employee
        }


        try {
            user.setStatus(1);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");
            user = userRepository.save(user);
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        } catch (Exception ex) {
            throw new UnexpectedException("Error saving User: " + ex.getLocalizedMessage());
        }


    }

    @Override
    public User updateUser(Integer id, User user) {
        if (Objects.isNull(user)) {
            throw new InvalidParameterException("Please provide user details");
        } else if (!id.equals(user.getId())) {
            throw new InvalidParameterException("Invalid details provided");
        }

        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with provided id not found");
        }


        if (checkUniqueUser(user.getUsername(), user.getId()) != null) {
            throw new UsernameAlreadyExistsException("Username '" + user.getUsername() + "' already exists");
        } else {
            Set<String> strRoles = user.getStrRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles != null) {

                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin" -> {
                            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                        }
                        case "user" -> {
                            Role userRole = roleRepository.findByName("ROLE_USER")
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                        }
                        case "super" -> {
                            Role superRole = roleRepository.findByName("ROLE_SUPER")
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(superRole);
                        }
                        default -> throw new RuntimeException("Error: Role is not found.");
                    }
                });
            }


            user.getRoles().addAll(roles);

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            //Username has to be unique (exception)
            //user.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                try {
                    return userRepository.save(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Error saving user.");
                }
            } else {
                throw new RuntimeException("User account is not activated, Please add a role to activate");
            }
        }


    }

    public User checkUniqueUser(String username, Integer id) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT c FROM User c WHERE c.username = :username and c.id<>:id ", User.class);
            return query.setParameter("username", username)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        User user;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception ex) {
            throw new UnexpectedException("Error getting user details, please try again: " + ex.getLocalizedMessage());
        }
        if (user != null) {
            return user;
        } else {
            throw new NotFoundException("User does not exist");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> findRoles() {
        return roleRepository.findAll();
    }
}
