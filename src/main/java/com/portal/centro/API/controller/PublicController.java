package com.portal.centro.API.controller;

import com.portal.centro.API.dto.RecoverPasswordDTO;
import com.portal.centro.API.dto.RequestCodeEmailDto;
import com.portal.centro.API.model.ObjectReturn;
import com.portal.centro.API.model.SendEmailCodeRecoverPassword;
import com.portal.centro.API.model.User;
import com.portal.centro.API.responses.DefaultResponse;
import com.portal.centro.API.service.EmailCodeService;
import com.portal.centro.API.service.PartnerService;
import com.portal.centro.API.service.UserService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
public class PublicController {

    private final PartnerService service;
    private final UserService userService;
    private final EmailCodeService emailCodeService;

    @Autowired
    public PublicController(PartnerService partnerService, UserService userService, EmailCodeService emailCodeService) {
        this.service = partnerService;
        this.userService = userService;
        this.emailCodeService = emailCodeService;
    }

    @GetMapping(path = "/academicos")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.findAllEstudantes());
    }


    @PostMapping(path = "/send-code-recover-password/{email}")
    public ResponseEntity<SendEmailCodeRecoverPassword> sendEmailCodeRecoverPassword(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.ok(userService.sendEmailCodeRecoverPassword(email));
    }

    @PostMapping("/request-verification")
    public ResponseEntity<?> requestVerification(@NotNull @RequestBody RequestCodeEmailDto emailDto) throws Exception {
        User user = userService.findByEmail(emailDto.getEmail());
        if (user != null) {
            this.emailCodeService.createCode(user);
            return ResponseEntity.ok(new ObjectReturn("OK"));
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping(path = "/recover-password")
    public ResponseEntity<DefaultResponse> recoverPassword(@RequestBody @Valid RecoverPasswordDTO recoverPasswordDTO) throws Exception {
        DefaultResponse defaultResponse = userService.recoverPassword(recoverPasswordDTO);
        return ResponseEntity.status(defaultResponse.getHttpStatus()).body(defaultResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody @Valid User requestBody) throws Exception {
        return ResponseEntity.ok(userService.save(requestBody));
    }

}
