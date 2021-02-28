package com.auth.validator;

import com.auth.domain.User;

import com.auth.domain.dto.UserDTO;
import com.auth.exceptions.InvalidParameterException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?>  aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserDTO user = (UserDTO) object;
       
        if(user.getPassword().length() <6){
            throw new InvalidParameterException("Password must be at least 6 characters");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            throw new InvalidParameterException("Passwords must match");

        }

    }
}
