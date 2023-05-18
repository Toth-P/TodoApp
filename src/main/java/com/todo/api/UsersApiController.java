package com.todo.api;

import com.todo.model.RegisterRequestDTO;
import com.todo.model.RegisterResponseDTO;
import com.todo.model.User;
import com.todo.model.UserLoginDTO;
import com.todo.security.AuthenticationRequest;
import com.todo.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UsersApiController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@RequestBody(required = false) RegisterRequestDTO registrationData) {
        return ResponseEntity.ok(userService.createUser(registrationData));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestBody(required = false) AuthenticationRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }


    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteuser(@ApiParam(value = "A unique identifier for an `user`.", required = true) @PathVariable("userId") Integer userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getuser(@ApiParam(value = "A unique identifier for an `user`.", required = true) @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }


    @GetMapping("/user")
    public ResponseEntity<List<User>> getusers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<Void> updateuser(@ApiParam(value = "A unique identifier for a `user`.", required = true) @PathVariable("userId") Integer userId, @ApiParam(value = "Updated `user` information.", required = true) @Valid @RequestBody RegisterRequestDTO user) {
        userService.updateUser(userId, user);
        return ResponseEntity.accepted().build();
    }

}

