package org.blogapplication.payloads;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.blogapplication.entities.Roles;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty(message = "name can not be empty!")
    @Size(min = 3, message = "name must be min of 3 characters!")
    private String name;

    @NotEmpty(message = "email can not be empty!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid Email! Please provide a valid domain." )
    private String email;

    @NotEmpty(message = "password can not be empty!")
    @Size(min = 4,max = 8, message = "Password must be min of 4 chars and max of 8 chars")
    private String password;

    @NotEmpty(message = "about can not be empty!")
    private String about;

    private Set<RoleDto> roles=new HashSet<>();

}
