package com.auth.service;

import com.auth.domain.Role;
import com.auth.domain.User;
import com.auth.domain.dto.UserDTO;


import java.util.List;

public interface UserService  {

    UserDTO saveUser(UserDTO user);
    User updateUser(Integer id,User user);
    User findByUsername(String username);
    List<User> findAll();
    List<Role>findRoles();

}
