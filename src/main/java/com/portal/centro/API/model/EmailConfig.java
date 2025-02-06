package com.portal.centro.API.model;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tb_email_config")
public class EmailConfig extends IModel {

    @NotBlank(message = "Parameter email is required.")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Parameter password is required.")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Parameter host is required.")
    @Column(name = "host")
    private String host;

    @NotNull(message = "Parameter port is required.")
    @Column(name = "port")
    private Integer port;

}

