package com.portal.centro.API.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendEmailCodeRecoverPassword {

    private String message;
    private String email;

}
