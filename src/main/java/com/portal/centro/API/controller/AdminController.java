package com.portal.centro.API.controller;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.model.User;
import com.portal.centro.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.portal.centro.API.enums.Type.ROLE_ADMIN;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("new-user")
    public ResponseEntity<?> addNewUserAdmin(@Valid @RequestBody User user) throws Exception {
        User loggedUser = userService.findSelfUser();

        if(loggedUser.getRole() != ROLE_ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(userService.saveAdmin(user));
    }

    // Valida se o usuário é ativo ou inativo
    //antes de deixar editar a role. Caso seja
    //ativo deixa editar e retorna o usuário
    //editado, caso contrario retornar uma
    //mensagem "não é possível editar um usuário
    //inativo"
    @PostMapping("edit/role/{id}")
    public ResponseEntity<?> editRoleAdmin(@RequestBody @Valid String role, @PathVariable Long id) throws Exception {
        User loggedUser = userService.findSelfUser();

        if(loggedUser.getRole() != ROLE_ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        role = role.replaceAll("\"", "");

        User idUser = userService.findOneById(id);

        if(idUser.getStatus().equals(StatusInactiveActive.ACTIVE)){
            return ResponseEntity.ok(userService.editUserRole(Type.valueOf(role), id));
        } else {
            return new ResponseEntity<>("Não é possível editar um usuário inativo.", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}



