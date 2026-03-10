package com.rial.orderspring.controller;

import com.rial.orderspring.dto.UserDTO;
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
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO loginDTO) throws Exception {
        return ResponseEntity.ok(userService.login(loginDTO.getEmail(), loginDTO.getPassword()));
    }
}
