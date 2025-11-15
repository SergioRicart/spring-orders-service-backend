package com.rial.orderspring.service;

import com.rial.orderspring.model.Client;

import java.util.List;

public interface ClientService {

    Client create(Client Client);

    List<Client> findAll();

    Client findById(String id);

    Client findByName(String name);

    Client findByPhone(String phone);

    Client findByEmail(String email);

    Client update(String id, Client updatedClient);

    void deleteById(String id);
}
