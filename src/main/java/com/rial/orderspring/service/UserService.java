package com.rial.orderspring.service;

import com.rial.orderspring.dto.UserRequest;
import com.rial.orderspring.dto.UserResponse;

public interface UserService {
    UserResponse create(UserRequest request);
    UserResponse findById(String id);
    UserResponse findByEmail(String email);
    void deleteById(String id);
    UserResponse login(String email, String password) throws Exception;
}
