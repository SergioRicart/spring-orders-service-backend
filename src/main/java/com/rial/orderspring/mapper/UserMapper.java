package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.UserDTO;
import com.rial.orderspring.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoleId(user.getRole() != null ? user.getRole().getId() : null);
        dto.setCompanyId(user.getCompany() != null ? user.getCompany().getId() : null);
        // password NO se expone en respuesta
        return dto;
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
