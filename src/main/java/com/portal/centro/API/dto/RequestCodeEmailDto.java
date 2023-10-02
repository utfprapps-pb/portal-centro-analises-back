package com.portal.centro.API.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;


@Data
public class RequestCodeEmailDto {
    @Email
    private String email;
}
