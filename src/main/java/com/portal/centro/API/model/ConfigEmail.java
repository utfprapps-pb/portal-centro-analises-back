package com.portal.centro.API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "configemail")
public class ConfigEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Parameter emailFrom is required.")
    @Column(name = "email_from")
    private String emailFrom;

    @NotBlank(message = "Parameter passwordEmailFrom is required.")
    @Column(name = "password_email_from")
    private String passwordEmailFrom;

    @NotBlank(message = "Parameter sendHost is required.")
    @Column(name = "send_host")
    private String sendHost;

    @NotNull(message = "Parameter sendPort is required.")
    @Column(name = "send_port")
    private Integer sendPort;

}

