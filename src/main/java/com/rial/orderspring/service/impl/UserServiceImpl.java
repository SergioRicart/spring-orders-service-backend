package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.request.UserRequest;
import com.rial.orderspring.dto.response.UserResponse;
import com.rial.orderspring.exception.UserNotFoundException;
import com.rial.orderspring.mapper.UserMapper;
import com.rial.orderspring.model.Company;
import com.rial.orderspring.model.User;
import com.rial.orderspring.repository.CompanyRepository;
import com.rial.orderspring.repository.UserRepository;
import com.rial.orderspring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository,
                           PasswordEncoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse create(UserRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));

        Company company;
        if (request.getCompanyId() == null || request.getCompanyId().isBlank()) {
            company = createDefaultCompany();
        } else {
            company = companyRepository.findById(request.getCompanyId()).orElseGet(this::createDefaultCompany);
        }
        user.setCompany(company);

        return userMapper.toResponse(userRepository.save(user));
    }

    private Company createDefaultCompany() {
        Company company = new Company();
        company.setName("MyCompany");
        company.setDescription("Default company created automatically");
        return companyRepository.save(company);
    }

    @Override
    public UserResponse findById(String id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public UserResponse findByEmail(String email) {
        return userMapper.toResponse(userRepository.findByEmail(email)
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
    public UserResponse login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        if (encoder.matches(password, user.getPassword())) {
            return userMapper.toResponse(user);
        } else {
            throw new Exception("PASS ERROR");
        }
    }
}
