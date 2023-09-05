package com.portal.centro.API.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

