package com.portal.centro.API.model;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity(name = "tb_partner")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner implements GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name must not be null!")
    @NotBlank(message = "Name must not be empty!")
    @Size(min = 4, max = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String cnpj;

    @NotNull
    private StatusInactiveActive status;

}
