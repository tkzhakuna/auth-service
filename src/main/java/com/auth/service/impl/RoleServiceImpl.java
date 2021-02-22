package com.auth.service.impl;

import com.auth.domain.Role;
import com.auth.exceptions.InvalidParameterException;
import com.auth.exceptions.NotFoundException;
import com.auth.exceptions.UnexpectedException;
import com.auth.repository.RoleRepository;
import com.auth.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addNew(Role role) {
        try {
            return roleRepository.save(role);
        }catch (Exception ex){
            throw new UnexpectedException("Role not saved: "+ex.getLocalizedMessage());
        }

    }

    @Override
    public Role update(Integer id,Role role) {
        if(Objects.isNull(role)){
            throw new InvalidParameterException("Please provide role details");
        }else if(!id.equals(role.getId())){
            throw new InvalidParameterException("Invalid details provided");
        }
        if(!roleRepository.existsById(id)){
            throw new NotFoundException("Role with provided id not found");
        }

        try{
            return roleRepository.save(role);
        }catch (Exception ex){
            throw new UnexpectedException("Role details not updated");
        }

    }

    @Override
    public Boolean deleteById(Integer id) {
        if(!roleRepository.existsById(id)){
            throw new NotFoundException("Role with provided id not found");
        }

        try{
            roleRepository.deleteById(id);
            return true;
        }catch(Exception ex){
            throw new UnexpectedException("Role not deleted: "+ex.getLocalizedMessage());
        }
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElseThrow(()->new NotFoundException("Role not found"));
    }
}
