package com.todo.service;

import com.todo.model.*;
import com.todo.model.*;
import com.todo.repository.UserRepository;
import com.todo.security.*;
import com.todo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtTokenUtil, MyUserDetailsService myUserDetailsService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = myUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public User save(RegisterRequestDTO user) {
        return userRepository.save(new User(user));
    }

    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public User findById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Integer userId, RegisterRequestDTO user) {
        User actualUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        actualUser.setUsername(user.getUsername());
        actualUser.setPassword(user.getPassword());

        userRepository.save(actualUser);
    }

    public RegisterResponseDTO createUser(RegisterRequestDTO registrationData) {
        PasswordSecurity passwordSecurity = PasswordSecurity.getInstance();
        User user = User.builder()
                .username(registrationData.getUsername())
                .password(passwordSecurity.encode(registrationData.getPassword()))
                .role(Role.ADMIN)
                .build();
        saveUser(user);

        return new RegisterResponseDTO(user.getUser_id(), user.getUsername());
    }

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    private void saveEncodedPassword(User user, String password) {
        PasswordSecurity passwordSecurity = PasswordSecurity.getInstance();
        user.setPassword(passwordSecurity.encode(password));

    }

    public UserLoginDTO loginUser(AuthenticationRequest loginRequest) {
        final UserDetails userDetails;
        userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return new UserLoginDTO(new AuthenticationResponse(jwt));
    }

    public User getUserByToken(String token) {
        String username = jwtTokenUtil.extractUsername(token);
        return userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
