package org.blogapplication.controllers;


import jakarta.validation.Valid;
import org.blogapplication.payloads.UserDto;
import org.blogapplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //POST - Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable int id, @RequestBody UserDto userDto){
        UserDto userDto1 = userService.updateUser(id, userDto);
        return ResponseEntity.ok(userDto1);
    }

    //DELETE - delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    //GET - get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtoList =  userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

    //GET - get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id){
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }
}
