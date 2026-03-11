package com.rial.orderspring.repository;

import com.rial.orderspring.model.Company;
import com.rial.orderspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    Optional<Company> findByName(String name);

    Optional<Company> findByUser(User user);

}
