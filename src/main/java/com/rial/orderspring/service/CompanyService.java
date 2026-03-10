package com.rial.orderspring.service;

import com.rial.orderspring.dto.CompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    CompanyDTO create(CompanyDTO companyDTO);

    Page<CompanyDTO> findAll(Pageable pageable);

    CompanyDTO findById(String id);

    CompanyDTO findByName(String name);

    CompanyDTO update(String id, CompanyDTO updatedCompanyDTO);

    void deleteById(String id);

}
