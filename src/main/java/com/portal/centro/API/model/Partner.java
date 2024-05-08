package com.portal.centro.API.model;

import com.portal.centro.API.enums.StatusInactiveActive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_partner")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

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
