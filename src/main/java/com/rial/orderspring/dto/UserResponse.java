package com.rial.orderspring.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String roleId;
    private String companyId;
}
