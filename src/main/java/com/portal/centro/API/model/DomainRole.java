package com.portal.centro.API.model;

import com.portal.centro.API.enums.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Table(
        name = "domain_role",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_domain_role", columnNames = "domain")
        }
)
@Entity
public class DomainRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String domain;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Type role;

}
