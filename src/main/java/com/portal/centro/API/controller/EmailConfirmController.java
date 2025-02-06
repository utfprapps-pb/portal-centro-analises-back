package com.portal.centro.API.controller;

import com.portal.centro.API.provider.ConfigFrontProvider;
import com.portal.centro.API.service.EmailCodeService;
import com.portal.centro.API.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/email-confirm")
public class EmailConfirmController {
    private final ConfigFrontProvider configFrontProvider;
    final EmailCodeService emailCodeService;
    final UserService userService;

    public EmailConfirmController(ConfigFrontProvider configFrontProvider, EmailCodeService emailCodeService,
                                  UserService userService) {
        this.configFrontProvider = configFrontProvider;
        this.emailCodeService = emailCodeService;
        this.userService = userService;
    }

    @GetMapping("/{code}")
    public RedirectView confirmEmail(@PathVariable("code") String hash) throws Exception {
        RedirectView redirectView = new RedirectView();

        redirectView.setUrl(configFrontProvider.getBaseurl() +
                (configFrontProvider.getPort() != null ? configFrontProvider.getPort() : "") +
                "/#/entrar?success=" + this.emailCodeService.confirmEmail(hash));
        return redirectView;
    }

}
