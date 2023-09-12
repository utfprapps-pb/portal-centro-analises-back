package com.portal.centro.API.controller;

import com.portal.centro.API.dto.StudentSolicitationDTO;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.StudentSolicitation;
import com.portal.centro.API.service.StudentSolicitationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("student-solicitation")
public class StudentSolicitationController extends GenericController<StudentSolicitation, Long> {

    private final StudentSolicitationService studentSolicitationService;

    public StudentSolicitationController(StudentSolicitationService studentSolicitationService) {
        super(studentSolicitationService);
        this.studentSolicitationService = studentSolicitationService;
    }

    @PostMapping("solicit")
    public ResponseEntity<StudentSolicitation> solicit(@RequestBody @Valid StudentSolicitationDTO studentSolicitationDTO) throws Exception {
        return ResponseEntity.ok(studentSolicitationService.solicit(studentSolicitationDTO));
    }

    @PostMapping("approve/{id}")
    public ResponseEntity<StudentSolicitation> approve(@PathVariable("id") Long studentSolicitationId) throws Exception {
        return ResponseEntity.ok(studentSolicitationService.approve(studentSolicitationId));
    }

    @PostMapping("refuse/{id}")
    public ResponseEntity<StudentSolicitation> refuse(@PathVariable("id") Long studentSolicitationId) throws Exception {
        return ResponseEntity.ok(studentSolicitationService.refuse(studentSolicitationId));
    }

}
