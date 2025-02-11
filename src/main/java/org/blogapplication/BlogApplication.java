package org.blogapplication;

import org.blogapplication.config.AppConstants;
import org.blogapplication.entities.Roles;
import org.blogapplication.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

    private RoleRepository roleRepository;

    public BlogApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public void run(String... args) throws Exception {
        try {
            // Check if roles are already seeded
            if (roleRepository.count() == 0) {
                Roles roleNormal = new Roles();
                roleNormal.setId(AppConstants.ROLE_NORMAL); // Should be 502
                roleNormal.setName("ROLE_NORMAL");

                Roles roleAdmin = new Roles();
                roleAdmin.setId(AppConstants.ROLE_ADMIN);   // Should be 501
                roleAdmin.setName("ROLE_ADMIN");

                List<Roles> roles = List.of(roleNormal, roleAdmin);
                List<Roles> result = roleRepository.saveAll(roles);
                result.forEach(r -> System.out.println("Seeded Role: " + r.getName()));
            } else {
                System.out.println("Roles are already seeded.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
