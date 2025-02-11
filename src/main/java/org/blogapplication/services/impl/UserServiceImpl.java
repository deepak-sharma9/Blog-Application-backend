package org.blogapplication.services.impl;


import org.blogapplication.config.AppConstants;
import org.blogapplication.entities.Roles;
import org.blogapplication.entities.User;

import org.blogapplication.exceptions.ResourceNotFoundException;
import org.blogapplication.mappers.UserMapper;
import org.blogapplication.payloads.UserDto;
import org.blogapplication.repositories.RoleRepository;
import org.blogapplication.repositories.UserRepository;
import org.blogapplication.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Fetch and assign the role (using safe retrieval)
        Roles role = roleRepository.findById(AppConstants.ROLE_NORMAL)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstants.ROLE_NORMAL));
        // Assuming user has a set of roles:
        user.getRoles().add(role);

        // Save the user
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto registerAdmin(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Roles adminRole = roleRepository.findById(AppConstants.ROLE_ADMIN).orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstants.ROLE_ADMIN));
        user.getRoles().add(adminRole);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);

    }


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.dtoToUser(userDto);
        //  Hash the password before saving
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {

        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        // Only update password if a new one is provided
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        User savedUser = userRepository.save(user);
        UserDto userDto1 = userMapper.userToDto(savedUser);

        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));

        return userMapper.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll();

        return users.stream().map(user -> userMapper.userToDto(user)).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }







}
