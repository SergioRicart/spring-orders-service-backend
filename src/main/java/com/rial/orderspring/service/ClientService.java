package com.rial.orderspring.service;

import com.rial.orderspring.dto.request.ClientRequest;
import com.rial.orderspring.dto.response.ClientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ClientResponse create(ClientRequest request);
    Page<ClientResponse> findAll(Pageable pageable);
    ClientResponse findById(String id);
    ClientResponse findByName(String name);
    ClientResponse findByPhone(String phone);
    ClientResponse findByEmail(String email);
    ClientResponse update(String id, ClientRequest request);
    void deleteById(String id);
}
