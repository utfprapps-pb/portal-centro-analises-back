package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.dto.ChangePasswordDTO;
import br.edu.utfpr.pb.app.labcaapi.dto.UserRawDto;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.model.ObjectReturn;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.model.UserBalance;
import br.edu.utfpr.pb.app.labcaapi.responses.DefaultResponse;
import br.edu.utfpr.pb.app.labcaapi.service.UserBalanceService;
import br.edu.utfpr.pb.app.labcaapi.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.edu.utfpr.pb.app.labcaapi.enums.Type.ROLE_ADMIN;

@RestController
@RequestMapping("/usuarios")
public class UserController extends GenericController<User, Long> {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserBalanceService userBalanceService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserBalanceService userBalanceService) {
        super(userService);
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userBalanceService = userBalanceService;
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<DefaultResponse> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) throws Exception {
        DefaultResponse defaultResponse = userService.changePassword(changePasswordDTO);
        return ResponseEntity.status(defaultResponse.getHttpStatus()).body(defaultResponse);
    }

    @GetMapping(path = "/role/{role}")
    public ResponseEntity<List<UserRawDto>> findUsersByRole(@PathVariable("role") String role) throws Exception {
        return ResponseEntity.ok(convertEntityListToRawDto(userService.findUsersByRole(role)));
    }

    @GetMapping(path = "/domain/{domain}")
    public ResponseEntity<List<UserRawDto>> findUsersByDomain(@PathVariable("domain") String domain) throws Exception {
        return ResponseEntity.ok(convertEntityListToRawDto(userService.findUsersByDomain(domain)));
    }

    @GetMapping(path = "/balance")
    public ResponseEntity<BigDecimal> findBalance() throws Exception {
        return ResponseEntity.ok(userBalanceService.getUserBalance());
    }

    @GetMapping(path = "/global-balance")
    public ResponseEntity<List<UserBalance>> findGlobalBalance() throws Exception {
        return ResponseEntity.ok(userBalanceService.getAll());
    }

    private List<UserRawDto> convertEntityListToRawDto(List<User> users) {
        List<UserRawDto> usersDto = new ArrayList<>();
        for (User user : users)
            usersDto.add(modelMapper.map(user, UserRawDto.class));
        return usersDto;
    }

    @Override
    public ResponseEntity<ObjectReturn> deleteById(@PathVariable Long id) throws Exception {
        throw new GenericException("Não é possivel excluir este tipo de Registro!");
    }

    @Override
    public ResponseEntity<User> update(@Valid @RequestBody User requestBody) throws Exception {
        User loggedUser = userService.findSelfUser();
        if(loggedUser.getRole() != ROLE_ADMIN && !loggedUser.getId().equals(requestBody.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(userService.update(requestBody));
    }
}
