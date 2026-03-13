package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.request.ClientRequest;
import com.rial.orderspring.dto.response.ClientResponse;
import com.rial.orderspring.exception.ClientNotFoundException;
import com.rial.orderspring.mapper.ClientMapper;
import com.rial.orderspring.model.Client;
import com.rial.orderspring.repository.ClientRepository;
import com.rial.orderspring.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponse create(ClientRequest request) {
        return clientMapper.toResponse(clientRepository.save(clientMapper.toEntity(request)));
    }

    @Override
    public Page<ClientResponse> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toResponse);
    }

    @Override
    public ClientResponse findById(String id) {
        return clientMapper.toResponse(clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id)));
    }

    @Override
    public ClientResponse findByName(String name) {
        return clientMapper.toResponse(clientRepository.findByName(name)
                .orElseThrow(() -> new ClientNotFoundException(name)));
    }

    @Override
    public ClientResponse findByPhone(String phone) {
        return clientMapper.toResponse(clientRepository.findByPhone(phone)
                .orElseThrow(() -> new ClientNotFoundException(phone)));
    }

    @Override
    public ClientResponse findByEmail(String email) {
        return clientMapper.toResponse(clientRepository.findByEmail(email)
                .orElseThrow(() -> new ClientNotFoundException(email)));
    }

    @Override
    public ClientResponse update(String id, ClientRequest request) {
        Client actual = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        actual.setName(request.getName());
        actual.setPhone(request.getPhone());
        actual.setEmail(request.getEmail());
        return clientMapper.toResponse(clientRepository.save(actual));
    }

    @Override
    public void deleteById(String id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client);
    }
}
