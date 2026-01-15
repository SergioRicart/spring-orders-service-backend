package com.rial.orderspring.model;

import com.rial.orderspring.constants.IBaseConstant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(schema = IBaseConstant.APP_SCHEMA, name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String email;

    private String password;

}
