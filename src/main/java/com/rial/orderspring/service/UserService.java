package com.rial.orderspring.service;

import com.rial.orderspring.dto.UserDTO;

public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO findById(String id);
    UserDTO findByEmail(String email);
    void deleteById(String id);
    UserDTO login(String email, String password) throws Exception;
}
