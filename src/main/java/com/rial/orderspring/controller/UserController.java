package com.rial.orderspring.controller;

import com.rial.orderspring.dto.UserRequest;
import com.rial.orderspring.dto.UserResponse;
import com.rial.orderspring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) throws Exception {
        return ResponseEntity.ok(userService.login(request.getEmail(), request.getPassword()));
    }
}
