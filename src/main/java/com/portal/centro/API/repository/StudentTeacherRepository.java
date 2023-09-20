package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.StudentTeacher;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface StudentTeacherRepository extends GenericRepository<StudentTeacher, Long> {

    @Query(nativeQuery = true,value ="Select * From student_teacher  where teacher=:teacherId")
    List<StudentTeacher> listByTeacherWhere(Long teacherId);

    @Query(value = "Select st.student From student_teacher as st where st.teacher.id=:teacherId")
    List<User> listStudentsByTeacher(Long teacherId);

    @Query(nativeQuery = true ,value = "Select * From student_teacher where student=:studentId")
    List<StudentTeacher> findByStudentWhere(Long studentId);

    StudentTeacher findByStudentIdAndAproved(Long studentId, Boolean aproved);

    Page<StudentTeacher> findAllByTeacherId(Long teacherId, Pageable pageable);

}
