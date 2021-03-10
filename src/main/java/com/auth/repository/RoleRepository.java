package com.auth.repository;

import com.auth.domain.ERole;
import com.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole role);
}
