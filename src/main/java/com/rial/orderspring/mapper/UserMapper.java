package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.UserRequest;
import com.rial.orderspring.dto.UserResponse;
import com.rial.orderspring.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRoleId(user.getRole() != null ? user.getRole().getId() : null);
        response.setCompanyId(user.getCompany() != null ? user.getCompany().getId() : null);
        return response;
    }

    public User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
}
