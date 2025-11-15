package com.rial.orderspring.repository;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByName(String name);

    Optional<Client> findByPhone(String phone);

    Optional<Client> findByEmail(String email);

}
