package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.UserDTO;
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

    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, PasswordEncoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {

        User user = userMapper.toEntity(userDTO);
        user.setPassword(encoder.encode(userDTO.getPassword()));

        Company company;
        if (userDTO.getCompanyId() == null || userDTO.getCompanyId().isBlank()){

            company = createDefaultCompany();

        }else{

            company = companyRepository.findById(userDTO.getCompanyId()).orElseGet(this::createDefaultCompany);

        }

        user.setCompany(company);

        return userMapper.toDTO(userRepository.save(user));
    }

    private Company createDefaultCompany() {
        Company company = new Company();
        company.setName("MyCompany");
        company.setDescription("Default company created automatically");
        return companyRepository.save(company);
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
