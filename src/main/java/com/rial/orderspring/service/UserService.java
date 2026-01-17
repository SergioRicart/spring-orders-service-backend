package com.rial.orderspring.service;

import com.rial.orderspring.model.User;

public interface UserService {

    User create(User user);

    User findById(String id);

    User findByEmail(String email);

    void deleteById(String id);

    User login(String email, String passwrod) throws Exception;

}
