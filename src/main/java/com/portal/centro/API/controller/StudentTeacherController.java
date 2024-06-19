package com.portal.centro.API.controller;

import com.portal.centro.API.dto.SolicitationRequestDto;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.ObjectReturn;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.StudentProfessor;
import com.portal.centro.API.model.User;
import com.portal.centro.API.service.StudentTeacherService;
import com.portal.centro.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/vinculos")
public class StudentTeacherController extends GenericController<StudentProfessor, Long> {

    private final StudentTeacherService studentTeacherService;

    private final UserService userService;

    @Autowired
    public StudentTeacherController(StudentTeacherService studentTeacherService, UserService userService) {
        super(studentTeacherService);
        this.studentTeacherService = studentTeacherService;
        this.userService = userService;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity alterStatus(@PathVariable(name = "id") Long id, @RequestBody Boolean approved) throws Exception {
        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN) && !Objects.equals(selfUser.getRole(), Type.ROLE_PROFESSOR)) {
            throw new GenericException("Você não pode fazer isso!");
        }
        studentTeacherService.updateBindStatus(id, approved);
        return ResponseEntity.ok(new ObjectReturn("Ok"));
    }

    @Override
    public ResponseEntity getAll() {
        return ResponseEntity.ok(studentTeacherService.getAllPendantsByUser());
    }

}
