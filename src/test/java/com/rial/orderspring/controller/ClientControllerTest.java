package com.rial.orderspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rial.orderspring.dto.request.ClientRequest;
import com.rial.orderspring.dto.response.ClientResponse;
import com.rial.orderspring.exception.ClientNotFoundException;
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

    private ClientResponse clientResponse;
    private ClientRequest clientRequest;

    @BeforeEach
    void setUp() {
        clientResponse = new ClientResponse();
        clientResponse.setId("123");
        clientResponse.setName("John Doe");
        clientResponse.setEmail("john@example.com");
        clientResponse.setPhone("123456789");

        clientRequest = new ClientRequest();
        clientRequest.setName("John Doe");
        clientRequest.setEmail("john@example.com");
        clientRequest.setPhone("123456789");
    }

    @Test
    void create_ShouldReturnCreatedClient() throws Exception {
        when(clientService.create(any(ClientRequest.class))).thenReturn(clientResponse);

        mockMvc.perform(post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(clientService, times(1)).create(any(ClientRequest.class));
    }

    @Test
    void findAll_ShouldReturnPageOfClients() throws Exception {
        Page<ClientResponse> page = new PageImpl<>(Arrays.asList(clientResponse));
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
        when(clientService.findById("123")).thenReturn(clientResponse);

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
        when(clientService.findByName("John Doe")).thenReturn(clientResponse);

        mockMvc.perform(get("/api/clients/get/name/John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(clientService, times(1)).findByName("John Doe");
    }

    @Test
    void findByPhone_ShouldReturnClient() throws Exception {
        when(clientService.findByPhone("123456789")).thenReturn(clientResponse);

        mockMvc.perform(get("/api/clients/get/phone/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

    @Test
    void findByEmail_ShouldReturnClient() throws Exception {
        when(clientService.findByEmail("john@example.com")).thenReturn(clientResponse);

        mockMvc.perform(get("/api/clients/get/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void update_ShouldReturnUpdatedClient() throws Exception {
        ClientRequest updateRequest = new ClientRequest();
        updateRequest.setName("Jane Doe");
        updateRequest.setEmail("jane@example.com");

        ClientResponse updatedClientResponse = new ClientResponse();
        updatedClientResponse.setId("123");
        updatedClientResponse.setName("Jane Doe");
        updatedClientResponse.setEmail("jane@example.com");

        when(clientService.update(eq("123"), any(ClientRequest.class))).thenReturn(updatedClientResponse);

        mockMvc.perform(put("/api/clients/update/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).update(eq("123"), any(ClientRequest.class));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        doNothing().when(clientService).deleteById("123");

        mockMvc.perform(delete("/api/clients/delete/123"))
                .andExpect(status().isNoContent());

        verify(clientService, times(1)).deleteById("123");
    }
}