package com.auth.service.impl;


import com.auth.domain.User;
import com.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	try {
    		
        User user = userRepository.findByUsername(username);
        if(user!=null) 
        return user.build(user);
    	}catch(UsernameNotFoundException unf) {
    		throw new UsernameNotFoundException("Invalid login");
    		
    	}catch(Exception ex) {
    		throw new RuntimeException("User not found: "+ex.getLocalizedMessage());
    	}
    	return null;
    }


}
