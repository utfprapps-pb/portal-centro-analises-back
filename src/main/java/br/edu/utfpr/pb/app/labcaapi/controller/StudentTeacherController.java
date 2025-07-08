package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.model.ObjectReturn;
import br.edu.utfpr.pb.app.labcaapi.model.StudentProfessor;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.service.StudentTeacherService;
import br.edu.utfpr.pb.app.labcaapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
