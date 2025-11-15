package com.rial.orderspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rial.orderspring.exception.ClientNotFoundException;
import com.rial.orderspring.model.Client;
import com.rial.orderspring.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;

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
    void create_ShouldReturnCreatedClient() throws Exception {
        when(clientService.create(any(Client.class))).thenReturn(client);

        mockMvc.perform(get("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(clientService, times(1)).create(any(Client.class));
    }

    @Test
    void findAll_ShouldReturnPageOfClients() throws Exception {
        Page<Client> page = new PageImpl<>(Arrays.asList(client));
        when(clientService.findAll(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/clients")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("123"))
                .andExpect(jsonPath("$.content[0].name").value("John Doe"));

        verify(clientService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void findById_WhenClientExists_ShouldReturnClient() throws Exception {
        when(clientService.findById("123")).thenReturn(client);

        mockMvc.perform(get("/api/clients/get/id/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(clientService, times(1)).findById("123");
    }

    @Test
    void findById_WhenClientNotExists_ShouldReturnNotFound() throws Exception {
        when(clientService.findById("999")).thenThrow(new ClientNotFoundException("999"));

        mockMvc.perform(get("/api/clients/get/id/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Client 999 not found"));
    }

    @Test
    void findByName_ShouldReturnClient() throws Exception {
        when(clientService.findByName("John Doe")).thenReturn(client);

        mockMvc.perform(get("/api/clients/get/name/John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(clientService, times(1)).findByName("John Doe");
    }

    @Test
    void findByPhone_ShouldReturnClient() throws Exception {
        when(clientService.findByPhone("123456789")).thenReturn(client);

        mockMvc.perform(get("/api/clients/get/phone/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

    @Test
    void findByEmail_ShouldReturnClient() throws Exception {
        when(clientService.findByEmail("john@example.com")).thenReturn(client);

        mockMvc.perform(get("/api/clients/get/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void update_ShouldReturnUpdatedClient() throws Exception {
        Client updatedClient = new Client();
        updatedClient.setName("Jane Doe");
        updatedClient.setEmail("jane@example.com");

        when(clientService.update(eq("123"), any(Client.class))).thenReturn(updatedClient);

        mockMvc.perform(put("/api/clients/update/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedClient)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).update(eq("123"), any(Client.class));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        doNothing().when(clientService).deleteById("123");

        mockMvc.perform(delete("/api/clients/delete/123"))
                .andExpect(status().isNoContent());

        verify(clientService, times(1)).deleteById("123");
    }
}