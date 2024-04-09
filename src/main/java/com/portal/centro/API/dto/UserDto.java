package com.portal.centro.API.dto;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private Type role;

    @NotNull(message = "Parameter name is required.")
    @Size(min = 4, max = 255)
    private String name;

    @NotNull(message = "Parameter email is required.")
    @Email
    private String email;

    @Enumerated
    private StatusInactiveActive status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    private BigDecimal balance;

    private String ra;

    private String siape;

    private String cpf;

    private String cnpj;

}
