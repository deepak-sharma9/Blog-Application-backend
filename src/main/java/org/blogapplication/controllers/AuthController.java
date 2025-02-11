package org.blogapplication.controllers;


import org.blogapplication.entities.User;
import org.blogapplication.payloads.JwtAuthRequest;
import org.blogapplication.payloads.JwtAuthResponse;
import org.blogapplication.payloads.RefreshTokenRequest;
import org.blogapplication.payloads.UserDto;
import org.blogapplication.repositories.UserRepository;
import org.blogapplication.security.JwtService;
import org.blogapplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private UserService userService;

    public AuthController(JwtService jwtService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        String accessToken = jwtService.generateToken(request.getUsername(), true);
        String refreshToken = jwtService.generateToken(request.getUsername(),false);
        User user = userRepository.findByEmail(request.getUsername()).get();
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(accessToken, refreshToken, user);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> freshToken(@RequestBody RefreshTokenRequest request){
        if (jwtService.validateToken(request.getRefreshToken())){
            String usernameFromToken = jwtService.getUserNameFromToken(request.getRefreshToken());
            String accessToken = jwtService.generateToken(usernameFromToken, true);
            String refreshToken = jwtService.generateToken(usernameFromToken,false);
            User user = userRepository.findByEmail(usernameFromToken).get();
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(accessToken, refreshToken, user);
            return ResponseEntity.ok(jwtAuthResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    //Register new user
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registerUser=userService.registerNewUser(userDto);

        return new ResponseEntity<UserDto>(registerUser, HttpStatus.CREATED);
    }

    //Register new admin, only accessible by admins
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registerAdmin")
    public ResponseEntity<UserDto> registerAdmin(@RequestBody UserDto userDto){
    UserDto registerUser = userService.registerAdmin(userDto);
    return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }






}
