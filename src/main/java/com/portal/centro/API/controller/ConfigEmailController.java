package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.ConfigEmail;
import com.portal.centro.API.service.ConfigEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email/config")
public class ConfigEmailController extends GenericController<ConfigEmail, Long> {

    final ConfigEmailService configEmailService;

    public ConfigEmailController(ConfigEmailService configEmailService, ConfigEmailService configEmailService1) {
        super(configEmailService);
        this.configEmailService = configEmailService1;
    }

    @Override
    public ResponseEntity getAll() {
        return ResponseEntity.ok(configEmailService.find());
    }

}
