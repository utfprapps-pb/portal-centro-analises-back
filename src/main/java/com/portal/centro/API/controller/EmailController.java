package com.portal.centro.API.controller;

import com.portal.centro.API.dto.EmailDto;
import com.portal.centro.API.service.EmailService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/email")
public class EmailController {

    final private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendingEmail(@RequestBody @Valid EmailDto emailDto) { //Test
        log.info("processando envio de email....");
        emailService.sendEmail(emailDto);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }
}
