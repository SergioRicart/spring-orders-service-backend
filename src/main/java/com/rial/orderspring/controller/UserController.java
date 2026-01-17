package com.rial.orderspring.controller;

import com.rial.orderspring.model.User;
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
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@PathVariable String email, @PathVariable String password) throws Exception {
        return ResponseEntity.ok(userService.login(email, password));
    }

}
