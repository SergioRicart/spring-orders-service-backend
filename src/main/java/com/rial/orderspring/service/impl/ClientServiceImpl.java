package com.rial.orderspring.service.impl;

import com.rial.orderspring.exception.ClientNotFoundException;
import com.rial.orderspring.model.Client;
import com.rial.orderspring.repository.ClientRepository;
import com.rial.orderspring.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(String id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(id)
        );
    }

    @Override
    public Client findByName(String name) {
        return clientRepository.findByName(name).orElseThrow(
                () -> new ClientNotFoundException(name)
        );
    }

    @Override
    public Client findByPhone(String phone) {
        return clientRepository.findByPhone(phone).orElseThrow(
                () -> new ClientNotFoundException(phone)
        );
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email).orElseThrow(
                () -> new ClientNotFoundException(email)
        );
    }

    @Override
    public Client update(String id, Client updatedClient) {
        Client actualClient = findById(id);

        BeanUtils.copyProperties(updatedClient, actualClient, "id");

        return clientRepository.save(actualClient);
    }

    @Override
    public void deleteById(String id) {

        Client productoExistente = findById(id);

        clientRepository.delete(productoExistente);
    }
}
