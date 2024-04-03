package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.StudentProfessor;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentProfessorRepository extends GenericRepository<StudentProfessor, Long> {

    @Query(nativeQuery = true, value = "Select * From tb_student_professor  where professor=:professorId")
    List<StudentProfessor> listByTeacherWhere(Long professorId);

    @Query(value = "Select st.student From StudentProfessor as st " +
            "where st.professor.id=:professorId and st.approved=:approvedStatus")
    List<User> listStudentsByProfessor(Long professorId, Boolean approvedStatus);

    @Query(nativeQuery = true, value = "Select * From tb_student_professor where student=:studentId")
    List<StudentProfessor> findByStudentWhere(Long studentId);

    StudentProfessor findByStudentIdAndApproved(Long studentId, Boolean approved);

    Page<StudentProfessor> findAllByProfessorId(Long professorId, Pageable pageable);

}
