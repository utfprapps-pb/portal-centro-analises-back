package com.portal.centro.API.dto;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.enums.UserType;
import com.portal.centro.API.model.User;
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

    @Enumerated
    private UserType type = UserType.PF;

    @Size(min = 4, max = 255)
    private String name;

    @Email
    private String email;

    @Enumerated
    private StatusInactiveActive status;

    private BigDecimal balance;

    private String raSiape;

    private String cpfCnpj;

}
