package com.rial.orderspring.service;

import com.rial.orderspring.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

    Client create(Client client);

    Page<Client> findAll(Pageable pageable);

    Client findById(String id);

    Client findByName(String name);

    Client findByPhone(String phone);

    Client findByEmail(String email);

    Client update(String id, Client updatedClient);

    void deleteById(String id);
}
