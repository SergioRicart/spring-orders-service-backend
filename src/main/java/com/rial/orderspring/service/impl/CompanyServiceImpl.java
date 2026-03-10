package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.CompanyDTO;
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
    public CompanyDTO create(CompanyDTO companyDTO) {

        Company company = companyMapper.toEntity(companyDTO);

        return companyDTO;
    }

    @Override
    public Page<CompanyDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CompanyDTO findById(String id) {
        return null;
    }

    @Override
    public CompanyDTO findByName(String name) {
        return null;
    }

    @Override
    public CompanyDTO update(String id, CompanyDTO updatedCompanyDTO) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
