package com.rial.orderspring.service;

import com.rial.orderspring.dto.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ClientDTO create(ClientDTO clientDTO);
    Page<ClientDTO> findAll(Pageable pageable);
    ClientDTO findById(String id);
    ClientDTO findByName(String name);
    ClientDTO findByPhone(String phone);
    ClientDTO findByEmail(String email);
    ClientDTO update(String id, ClientDTO updatedClientDTO);
    void deleteById(String id);
}
