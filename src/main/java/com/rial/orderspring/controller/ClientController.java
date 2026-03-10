package com.rial.orderspring.controller;

import com.rial.orderspring.dto.ClientDTO;
import com.rial.orderspring.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.create(clientDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(clientService.findAll(pageable));
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity<ClientDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(clientService.findByName(name));
    }

    @GetMapping("/get/phone/{phone}")
    public ResponseEntity<ClientDTO> findByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(clientService.findByPhone(phone));
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<ClientDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.findByEmail(email));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable String id, @RequestBody ClientDTO updatedClientDTO) {
        return ResponseEntity.ok(clientService.update(id, updatedClientDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
