package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.StudentProfessor;
import com.portal.centro.API.model.User;
import com.portal.centro.API.service.StudentTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student-teacher")
public class StudentTeacherController extends GenericController<StudentProfessor, Long> {

    private final StudentTeacherService studentTeacherService;

    @Autowired
    public StudentTeacherController(StudentTeacherService studentTeacherService) {
        super(studentTeacherService);
        this.studentTeacherService = studentTeacherService;
    }

    // busca a lista de VINCULOS pelo id do professor
    @GetMapping(path = "/listByTeacher/{idProfessor}")
    public ResponseEntity<List<StudentProfessor>> listByTeacher(@PathVariable Long idProfessor){
        return ResponseEntity.ok(studentTeacherService.listByProfessor(idProfessor));
    }

    // busca a lista de ALUNOS pelo id do professor
    @GetMapping(path = "/listStudentsByTeacher/{idProfessor}")
    public ResponseEntity<List<User>> listStudentsByTeacher(@PathVariable Long idProfessor){
        return ResponseEntity.ok(studentTeacherService.listStudentsByTeacher(idProfessor));
    }

    // busca o professor pelo id do aluno
    @GetMapping(path = "/findByStudent/{idAluno}")
    public ResponseEntity<List<StudentProfessor>> findByStudent(@PathVariable Long idAluno){
        return ResponseEntity.ok(studentTeacherService.findByStudent(idAluno));
    }

    @Override
    public ResponseEntity getAll() throws Exception {
        return ResponseEntity.ok(studentTeacherService.getAllByUser());
    }

    @GetMapping(path = "/listByTeacherPage")
    public Page<StudentProfessor> listByTeacherPage(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order",required = false) String order,
            @RequestParam(value = "asc",required = false) Boolean asc,
            @RequestParam(value = "userid") Long idProfessor){
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return studentTeacherService.listByTeacherPage(idProfessor, pageRequest);
    }

}
