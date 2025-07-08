package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.dto.RecoverPasswordDTO;
import br.edu.utfpr.pb.app.labcaapi.dto.RequestCodeEmailDto;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.model.ObjectReturn;
import br.edu.utfpr.pb.app.labcaapi.model.SendEmailCodeRecoverPassword;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.responses.DefaultResponse;
import br.edu.utfpr.pb.app.labcaapi.service.EmailCodeService;
import br.edu.utfpr.pb.app.labcaapi.service.PartnerService;
import br.edu.utfpr.pb.app.labcaapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity requestVerification(@RequestBody RequestCodeEmailDto emailDto) throws Exception {
        User user = userService.findByEmail(emailDto.getEmail());
        if (user != null) {
            this.emailCodeService.createCode(user);
            return ResponseEntity.ok(new ObjectReturn("OK"));
        }
        throw new GenericException("E-mail não encontrado");
    }

    @PostMapping("/recover-password")
    public ResponseEntity<DefaultResponse> recoverPassword(@RequestBody @Valid RecoverPasswordDTO recoverPasswordDTO) throws Exception {
        DefaultResponse defaultResponse = userService.recoverPassword(recoverPasswordDTO);
        return ResponseEntity.status(defaultResponse.getHttpStatus()).body(defaultResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody @Valid User requestBody) throws Exception {
        return ResponseEntity.ok(userService.save(requestBody));
    }

}
