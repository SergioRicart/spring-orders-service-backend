package com.rial.orderspring.dto.response;

import com.rial.orderspring.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CompanyResponse {
    private String id;
    private String name;
    private String description;
    private List<User> users;
}
