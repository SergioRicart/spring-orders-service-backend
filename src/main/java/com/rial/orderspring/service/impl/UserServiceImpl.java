package com.rial.orderspring.service.impl;

import com.rial.orderspring.exception.UserNotFoundException;
import com.rial.orderspring.model.User;
import com.rial.orderspring.repository.UserRepository;
import com.rial.orderspring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User create(User user) {

        User newUser = new User();

        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(newUser);

    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public void deleteById(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User login(String email, String password) throws Exception {

        User user = findByEmail(email);

        if (user.getPassword().equals(encoder.encode(user.getPassword()))){
            return user;
        }else {
            throw new Exception("PASS ERROR");
        }
    }
}
