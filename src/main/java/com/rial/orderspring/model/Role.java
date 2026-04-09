package com.rial.orderspring.model;

import com.rial.orderspring.constants.IBaseConstant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(schema = IBaseConstant.APP_SCHEMA, name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Permission> permissions;

}
