package com.portal.centro.API.controller;

import com.portal.centro.API.service.EmailCodeService;
import com.portal.centro.API.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("email-confirm")
public class EmailConfirmController {

    final EmailCodeService emailCodeService;
    final UserService userService;

    public EmailConfirmController(EmailCodeService emailCodeService, UserService userService) {
        this.emailCodeService = emailCodeService;
        this.userService = userService;
    }

    @GetMapping("code/{code}")
    public ResponseEntity<?> confirmEmail(@PathVariable("code") String hash) throws Exception {
        return ResponseEntity.ok(this.emailCodeService.confirmEmail(hash));
    }

}
