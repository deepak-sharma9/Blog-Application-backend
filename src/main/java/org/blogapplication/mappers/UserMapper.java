package org.blogapplication.mappers;

import org.blogapplication.entities.User;
import org.blogapplication.payloads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User dtoToUser(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
