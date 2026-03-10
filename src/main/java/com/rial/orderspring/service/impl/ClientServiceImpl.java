package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.ClientDTO;
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
    public ClientDTO create(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Override
    public Page<ClientDTO> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toDTO);
    }

    @Override
    public ClientDTO findById(String id) {
        return clientMapper.toDTO(clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id)));
    }

    @Override
    public ClientDTO findByName(String name) {
        return clientMapper.toDTO(clientRepository.findByName(name)
                .orElseThrow(() -> new ClientNotFoundException(name)));
    }

    @Override
    public ClientDTO findByPhone(String phone) {
        return clientMapper.toDTO(clientRepository.findByPhone(phone)
                .orElseThrow(() -> new ClientNotFoundException(phone)));
    }

    @Override
    public ClientDTO findByEmail(String email) {
        return clientMapper.toDTO(clientRepository.findByEmail(email)
                .orElseThrow(() -> new ClientNotFoundException(email)));
    }

    @Override
    public ClientDTO update(String id, ClientDTO updatedClientDTO) {
        Client actual = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        actual.setName(updatedClientDTO.getName());
        actual.setPhone(updatedClientDTO.getPhone());
        actual.setEmail(updatedClientDTO.getEmail());
        return clientMapper.toDTO(clientRepository.save(actual));
    }

    @Override
    public void deleteById(String id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client);
    }
}
