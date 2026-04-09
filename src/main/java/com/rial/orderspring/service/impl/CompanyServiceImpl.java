package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.request.CompanyRequest;
import com.rial.orderspring.dto.response.CompanyResponse;
import com.rial.orderspring.mapper.CompanyMapper;
import com.rial.orderspring.model.Company;
import com.rial.orderspring.repository.CompanyRepository;
import com.rial.orderspring.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public CompanyResponse create(CompanyRequest companyRequest) {

        Company company = companyMapper.toEntity(companyRequest);

        return companyMapper.toDTO(company);
    }

    @Override
    public Page<CompanyResponse> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CompanyResponse findById(String id) {
        return null;
    }

    @Override
    public CompanyResponse findByName(String name) {
        return null;
    }

    @Override
    public CompanyResponse update(String id, CompanyRequest updatedCompanyRequest) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
