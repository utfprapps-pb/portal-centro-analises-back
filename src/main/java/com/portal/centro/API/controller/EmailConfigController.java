package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.EmailConfig;
import com.portal.centro.API.service.EmailConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email/config")
public class EmailConfigController extends GenericController<EmailConfig, Long> {

    final EmailConfigService emailConfigService;

    public EmailConfigController(EmailConfigService emailConfigService, EmailConfigService emailConfigService1) {
        super(emailConfigService);
        this.emailConfigService = emailConfigService1;
    }

    @Override
    public ResponseEntity<EmailConfig> getAll() throws Exception {
        return ResponseEntity.ok(emailConfigService.find());
    }

}
