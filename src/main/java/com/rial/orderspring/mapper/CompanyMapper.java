package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.request.CompanyRequest;
import com.rial.orderspring.dto.response.CompanyResponse;
import com.rial.orderspring.model.Company;

public class CompanyMapper {

    public CompanyResponse toDTO(Company company) {
        CompanyResponse dto = new CompanyResponse();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setDescription(company.getDescription());
        dto.setUsers(company.getUsers());

        return dto;
    }

    public Company toEntity(CompanyRequest request) {
        Company company = new Company();
        company.setName(request.getName());
        company.setDescription(request.getDescription());
        return company;
    }
}
