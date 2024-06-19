package com.portal.centro.API.controller;

import com.portal.centro.API.dto.ChangePasswordDTO;
import com.portal.centro.API.dto.UserDto;
import com.portal.centro.API.dto.UserRawDto;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.ObjectReturn;
import com.portal.centro.API.model.User;
import com.portal.centro.API.responses.DefaultResponse;
import com.portal.centro.API.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.portal.centro.API.enums.Type.ROLE_ADMIN;

@RestController
@RequestMapping("/usuarios")
public class UserController extends GenericController<User, Long> {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        super(userService);
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @PostMapping(path = "/change-password")
    public ResponseEntity<DefaultResponse> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) throws Exception {
        DefaultResponse defaultResponse = userService.changePassword(changePasswordDTO);
        return ResponseEntity.status(defaultResponse.getHttpStatus()).body(defaultResponse);
    }
//
//    @GetMapping(path = "/find-self-user")
//    public ResponseEntity<UserDto> findSelfUser() {
//        return ResponseEntity.ok(convertEntityToDto(userService.findSelfUser()));
//    }

    @GetMapping(path = "/role/{role}")
    public ResponseEntity<List<UserRawDto>> findUsersByRole(@PathVariable("role") String role) throws Exception {
        return ResponseEntity.ok(convertEntityListToRawDto(userService.findUsersByRole(role)));
    }

    private UserDto convertEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private List<UserRawDto> convertEntityListToRawDto(List<User> users) {
        List<UserRawDto> usersDto = new ArrayList<>();
        for (User user : users)
            usersDto.add(modelMapper.map(user, UserRawDto.class));
        return usersDto;
    }

    private List<UserDto> convertEntityListToDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users)
            usersDto.add(convertEntityToDto(user));
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
