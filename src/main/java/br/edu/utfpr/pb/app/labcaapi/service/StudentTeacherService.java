package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.enums.StudentTeacherApproved;
import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.StudentProfessor;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.repository.StudentProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentTeacherService extends GenericService<StudentProfessor, Long> {

    private final StudentProfessorRepository studentProfessorRepository;
    private final UserService userService;

    @Autowired
    public StudentTeacherService(
            StudentProfessorRepository studentProfessorRepository, UserService userService) {
        super(studentProfessorRepository);
        this.userService = userService;
        this.studentProfessorRepository = studentProfessorRepository;
    }

    @Override
    public StudentProfessor save(StudentProfessor requestBody) throws Exception {
        if (requestBody.getId() == null) {
            User self = userService.findSelfUser();
            if (Type.ROLE_STUDENT.equals(self.getRole())) {
                requestBody.setApproved(StudentTeacherApproved.PENDENTE);
            } else {
                requestBody.setApproved(StudentTeacherApproved.ACEITO);
            }
        }
        List<StudentProfessor> allByStudentAndProfessor = studentProfessorRepository.findAllByStudentAndProfessor(requestBody.getStudent().getId(), requestBody.getProfessor().getId());
        boolean pendenciaExistente = allByStudentAndProfessor.stream().anyMatch(it -> StudentTeacherApproved.PENDENTE.equals(it.getApproved()));
        if (pendenciaExistente) {
            throw new GenericException("Professor e Aluno já possuem um vínculo pendente de Aprovação/Recusa");
        }
        boolean vinculoJaAceito = allByStudentAndProfessor.stream().anyMatch(it -> StudentTeacherApproved.ACEITO.equals(it.getApproved()) && it.getProfessor().getId().equals(requestBody.getProfessor().getId()));
        if (vinculoJaAceito) {
            throw new GenericException("Professor e Aluno já possuem um vínculo Ativo!");
        }

        requestBody.setCreatedAt(LocalDate.now());
        return super.save(requestBody);
    }

    public void updateBindStatus(Long id, boolean approved) {
        studentProfessorRepository.updateApproved(id, approved ? StudentTeacherApproved.ACEITO : StudentTeacherApproved.RECUSADO);
    }

    public List<StudentProfessor> getAllPendantsByUser() {
        User user = userService.findSelfUser();
        List<StudentProfessor> all = studentProfessorRepository.findAll();

        return switch (user.getRole()) {
            case ROLE_ADMIN -> all;
            case ROLE_PROFESSOR -> all.stream().filter(it -> user.getId().equals(it.getProfessor().getId())).toList();
            case ROLE_STUDENT -> all.stream().filter(it -> user.getId().equals(it.getStudent().getId())).toList();
            case ROLE_PARTNER, ROLE_EXTERNAL -> new ArrayList<>();
        };
    }

}
