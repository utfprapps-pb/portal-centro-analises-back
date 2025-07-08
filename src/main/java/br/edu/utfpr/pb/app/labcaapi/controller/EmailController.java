package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.dto.EmailDto;
import br.edu.utfpr.pb.app.labcaapi.service.EmailService;
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
    public ResponseEntity<?> sendingEmail(@RequestBody @Valid EmailDto emailDto) throws Exception {
        log.info("processando envio de email....");
        emailService.sendEmail(emailDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
