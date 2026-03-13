package com.rial.orderspring.service;

import com.rial.orderspring.dto.request.CompanyRequest;
import com.rial.orderspring.dto.response.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    CompanyResponse create(CompanyRequest companyRequest);

    Page<CompanyResponse> findAll(Pageable pageable);

    CompanyResponse findById(String id);

    CompanyResponse findByName(String name);

    CompanyResponse update(String id, CompanyRequest updatedCompanyRequest);

    void deleteById(String id);

}
