package org.blogapplication.security;

import org.blogapplication.entities.User;
import org.blogapplication.exceptions.ResourceNotFoundException;
import org.blogapplication.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {


    private UserRepository userRepository;
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email: "+username,0));
        return user;
    }
}
