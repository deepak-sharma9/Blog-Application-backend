package org.blogapplication.config;

import org.blogapplication.entities.Roles;
import org.blogapplication.entities.User;
import org.blogapplication.exceptions.ResourceNotFoundException;
import org.blogapplication.repositories.RoleRepository;
import org.blogapplication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public AdminInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail("admin@gmail.com").isEmpty()){
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setAbout("Initial admin account");
            Roles adminRole = roleRepository.findById(AppConstants.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Admin role not found"));
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
        }

    }
}