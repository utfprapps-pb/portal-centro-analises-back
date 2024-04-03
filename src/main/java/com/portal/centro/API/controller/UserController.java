package com.portal.centro.API.controller;

import com.portal.centro.API.dto.ChangePasswordDTO;
import com.portal.centro.API.dto.RecoverPasswordDTO;
import com.portal.centro.API.dto.RequestCodeEmailDto;
import com.portal.centro.API.dto.UserDto;
import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.ObjectReturn;
import com.portal.centro.API.model.SendEmailCodeRecoverPassword;
import com.portal.centro.API.model.User;
import com.portal.centro.API.responses.DefaultResponse;
import com.portal.centro.API.service.EmailCodeService;
import com.portal.centro.API.service.UserService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends GenericController<User, Long> {

    private final EmailCodeService emailCodeService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(EmailCodeService emailCodeService, UserService userService, ModelMapper modelMapper) {
        super(userService);
        this.emailCodeService = emailCodeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "/send-code-recover-password/email/{email}")
    public ResponseEntity<SendEmailCodeRecoverPassword> sendEmailCodeRecoverPassword(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.ok(userService.sendEmailCodeRecoverPassword(email));
    }

    @PostMapping(path = "/recover-password")
    public ResponseEntity<DefaultResponse> recoverPassword(@RequestBody @Valid RecoverPasswordDTO recoverPasswordDTO) throws Exception {
        DefaultResponse defaultResponse = userService.recoverPassword(recoverPasswordDTO);
        return ResponseEntity.status(defaultResponse.getHttpStatus()).body(defaultResponse);
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<DefaultResponse> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) throws Exception {
        DefaultResponse defaultResponse = userService.changePassword(changePasswordDTO);
        return ResponseEntity.status(defaultResponse.getHttpStatus()).body(defaultResponse);
    }

    @GetMapping(path = "/find-self-user")
    public ResponseEntity<UserDto> findSelfUser() {
        return ResponseEntity.ok(convertEntityToDto(userService.findSelfUser()));
    }

    @GetMapping(path = "role/{role}")
    public ResponseEntity<List<UserDto>> findUsersByRole(@PathVariable("role") String role) {
        return ResponseEntity.ok(convertEntityListToDto(userService.findUsersByRole(role)));
    }

    private UserDto convertEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private List<UserDto> convertEntityListToDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users)
            usersDto.add(convertEntityToDto(user));
        return usersDto;
    }

    //chama a função que vai inativar ou deletar o usuário
    //dependendo dos vínculos dele com projetos
    @Override
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.editUserStatusToInactiveOrDelete(id));
    }

    /**
     * Retorna uma lista de usuários ativos
     */
    @Override
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAllUsersActivatedOrInactivated(StatusInactiveActive.ACTIVE));
    }

    /**
     * Retorna uma lista de usuários inativos
     */
    @GetMapping(path = "find-inactive")
    public ResponseEntity<List<User>> findAllInactive() {
        return ResponseEntity.ok(userService.findAllUsersActivatedOrInactivated(StatusInactiveActive.INACTIVE));
    }

    /**
     * Atualiza a situação do usuário para ativo
     */
    @PutMapping("activate-user/{id}")
    public ResponseEntity<User> activeUserById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.editUserStatusToActive(id));
    }

    @GetMapping("page-role")
    public Page<UserDto> pageUser(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "asc", required = false) Boolean asc,
            @RequestParam(value = "role", required = false) String role
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        Page<User> list = userService.findUsersByRolePaged(role, pageRequest);

        return list.map(this::convertEntityToDto);
    }

    @GetMapping("page-status")
    public Page<UserDto> pageUser(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "asc", required = false) Boolean asc,
            @RequestParam(value = "active", required = false) Boolean active
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        Page<User> list = userService.findUsersByStatusPaged(active ? StatusInactiveActive.ACTIVE : StatusInactiveActive.INACTIVE, pageRequest);

        return list.map(this::convertEntityToDto);
    }

    /**
     * Realiza um reenvio do email de verificação de conta.
     */
    @PostMapping("/request-verification")
    public ResponseEntity<?> requestVerification(@NotNull @RequestBody RequestCodeEmailDto emailDto) throws Exception {
        User user = userService.findByEmail(emailDto.getEmail());
        if (user != null) {
            this.emailCodeService.createCode(user);
            return ResponseEntity.ok(new ObjectReturn("OK"));
        }

        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
