package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.CompanyDTO;
import com.rial.orderspring.model.Company;

public class CompanyMapper {

    public CompanyDTO toDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setDescription(company.getDescription());
        dto.setUsers(company.getUsers());

        return dto;
    }

    public Company toEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setId(dto.getId());
        company.setName(dto.getName());
        company.setDescription(dto.getDescription());
        company.setUsers(dto.getUsers());
        return company;
    }
}
