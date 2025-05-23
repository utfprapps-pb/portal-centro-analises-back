package com.portal.centro.API.repository;

import com.portal.centro.API.enums.StudentTeacherApproved;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.StudentProfessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentProfessorRepository extends GenericRepository<StudentProfessor, Long> {

    @Query(nativeQuery = true, value = "select * from tb_student_professor t where student=:studentId and professor=:professorId")
    List<StudentProfessor> findAllByStudentAndProfessor(Long studentId, Long professorId);

    @Query(nativeQuery = true, value = "select * from tb_student_professor t where student=:studentId and approved=:approved")
    List<StudentProfessor> findAllByStudentAndApproved(Long studentId, StudentTeacherApproved approved);

    @Modifying
    @Transactional
    @Query("update tb_student_professor set approved=:approved where id=:id")
    void updateApproved(Long id, StudentTeacherApproved approved);

}
