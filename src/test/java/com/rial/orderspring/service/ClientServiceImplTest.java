package com.rial.orderspring.service;

import com.rial.orderspring.exception.ClientNotFoundException;
import com.rial.orderspring.model.Client;
import com.rial.orderspring.repository.ClientRepository;
import com.rial.orderspring.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("123");
        client.setName("John Doe");
        client.setEmail("john@example.com");
        client.setPhone("123456789");
    }

    @Test
    void create_ShouldReturnSavedClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.create(client);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void findAll_ShouldReturnPageOfClients() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Client> page = new PageImpl<>(Arrays.asList(client));
        when(clientRepository.findAll(pageable)).thenReturn(page);

        Page<Client> result = clientService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(clientRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_WhenClientExists_ShouldReturnClient() {
        when(clientRepository.findById("123")).thenReturn(Optional.of(client));

        Client result = clientService.findById("123");

        assertNotNull(result);
        assertEquals("123", result.getId());
        verify(clientRepository, times(1)).findById("123");
    }

    @Test
    void findById_WhenClientNotExists_ShouldThrowException() {
        when(clientRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            clientService.findById("999");
        });
    }

    @Test
    void findByName_WhenClientExists_ShouldReturnClient() {
        when(clientRepository.findByName("John Doe")).thenReturn(Optional.of(client));

        Client result = clientService.findByName("John Doe");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void findByEmail_WhenClientExists_ShouldReturnClient() {
        when(clientRepository.findByEmail("john@example.com")).thenReturn(Optional.of(client));

        Client result = clientService.findByEmail("john@example.com");

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void update_ShouldReturnUpdatedClient() {
        Client updatedClient = new Client();
        updatedClient.setName("Jane Doe");
        updatedClient.setEmail("jane@example.com");
        updatedClient.setPhone("987654321");

        when(clientRepository.findById("123")).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.update("123", updatedClient);

        assertNotNull(result);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void deleteById_WhenClientExists_ShouldDeleteClient() {
        when(clientRepository.findById("123")).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);

        clientService.deleteById("123");

        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    void deleteById_WhenClientNotExists_ShouldThrowException() {
        when(clientRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            clientService.deleteById("999");
        });
    }
}