package com.rial.orderspring.controller;

import com.rial.orderspring.model.Client;
import com.rial.orderspring.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/create")
    public ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.create(client)) ;
    }

    @GetMapping
    public ResponseEntity<Page<Client>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size ) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(clientService.findAll(pageable));
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return ResponseEntity.ok(clientService.findByName(name));
    }

    @GetMapping("/get/phone/{phone}")
    public ResponseEntity<?> findByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(clientService.findByPhone(phone));

    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.findByEmail(email));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Client updatedClient) {

        Client client = clientService.update(id, updatedClient);

        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {

        clientService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
