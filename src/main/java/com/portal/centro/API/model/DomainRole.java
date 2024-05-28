package com.portal.centro.API.model;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.generic.crud.GenericModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Table(
        name = "tb_domain_role",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_domain_role", columnNames = "domain")
        }
)
@Entity
public class DomainRole implements GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String domain;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Type role;

}
