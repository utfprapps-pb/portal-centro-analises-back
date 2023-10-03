package com.portal.centro.API.controller;

import com.portal.centro.API.dto.RequestCodeEmailDto;
import com.portal.centro.API.model.User;
import com.portal.centro.API.service.EmailCodeService;
import com.portal.centro.API.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("emailconfirm")
public class EmailConfirmController {

    final EmailCodeService emailCodeService;
    final UserService userService;

    public EmailConfirmController(EmailCodeService emailCodeService, UserService userService) {
        this.emailCodeService = emailCodeService;
        this.userService = userService;
    }

    @GetMapping("code/{code}")
    public ResponseEntity confirmEmail(@PathVariable("code") String hash) throws Exception {
        return ResponseEntity.ok(this.emailCodeService.confirmEmail(hash));
    }

    @PostMapping("/request_verification")
    public ResponseEntity requestVerification(@NotNull @RequestBody RequestCodeEmailDto emailDto) throws Exception {
        User user = userService.findByEmail(emailDto.getEmail());

        if(user != null) {
            this.emailCodeService.createCode(user);
            return ResponseEntity.ok("send email");
        }

        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
