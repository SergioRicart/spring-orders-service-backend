package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.UserDTO;
import com.rial.orderspring.exception.UserNotFoundException;
import com.rial.orderspring.mapper.UserMapper;
import com.rial.orderspring.model.User;
import com.rial.orderspring.repository.UserRepository;
import com.rial.orderspring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO findById(String id) {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public UserDTO findByEmail(String email) {
        return userMapper.toDTO(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email)));
    }

    @Override
    public void deleteById(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        if (encoder.matches(password, user.getPassword())) {
            return userMapper.toDTO(user);
        } else {
            throw new Exception("PASS ERROR");
        }
    }
}
