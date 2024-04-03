package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.StudentProfessor;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.StudentProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        StudentProfessor studentProfessorDb = studentProfessorRepository.findByStudentIdAndApproved(requestBody.getStudent().getId(), true);
        if (studentProfessorDb != null) {
            throw new ValidationException("Este aluno já esta vinculado a um professor.");
        }

        requestBody.setCreatedAt(LocalDate.now());

        return super.save(requestBody);
    }

    public List<StudentProfessor> listByProfessor(Long professorId) {
        return studentProfessorRepository.listByTeacherWhere(professorId);
    }

    public List<User> listStudentsByTeacher(Long professorId) {
        return studentProfessorRepository.listStudentsByProfessor(professorId, true);
    }
    public List<StudentProfessor> findByStudent(Long studentId) {
        return studentProfessorRepository.findByStudentWhere(studentId);
    }

    public List<StudentProfessor> getAllByUser() {
        User user = userService.findSelfUser();

        if(user.getRole() == Type.ROLE_PROFESSOR) {
            return studentProfessorRepository.listByTeacherWhere(user.getId());
        } else {
            return studentProfessorRepository.findByStudentWhere(user.getId());
        }
    }

    public Page<StudentProfessor> listByTeacherPage(Long teacherId, PageRequest pageRequest) {
        return studentProfessorRepository.findAllByProfessorId(teacherId, pageRequest);
    }

}
