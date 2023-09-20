package com.portal.centro.API.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Null;

@Data
public class RequestCodeEmailDto {
    @Email
    private String email;
}
