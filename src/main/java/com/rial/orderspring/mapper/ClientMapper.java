package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.ClientRequest;
import com.rial.orderspring.dto.ClientResponse;
import com.rial.orderspring.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientResponse toResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setPhone(client.getPhone());
        response.setEmail(client.getEmail());
        return response;
    }

    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setPhone(request.getPhone());
        client.setEmail(request.getEmail());
        return client;
    }
}
