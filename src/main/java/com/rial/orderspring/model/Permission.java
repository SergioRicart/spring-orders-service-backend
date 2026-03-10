package com.rial.orderspring.model;

import com.rial.orderspring.constants.IBaseConstant;
import com.rial.orderspring.enums.Page;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(schema = IBaseConstant.APP_SCHEMA, name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Page page;

    private boolean view;

    private boolean edit;

    private boolean crete;

    private boolean delete;

}
