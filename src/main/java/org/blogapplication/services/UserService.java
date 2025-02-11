package org.blogapplication.services;

import org.blogapplication.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto registerAdmin(UserDto userDto);
    UserDto createUser(UserDto userDto);
    UserDto updateUser( Integer userId, UserDto userDto);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);



}
