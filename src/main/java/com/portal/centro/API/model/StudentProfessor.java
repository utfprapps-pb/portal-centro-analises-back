package com.portal.centro.API.model;

import com.portal.centro.API.enums.StudentTeacherApproved;
import com.portal.centro.API.generic.crud.GenericModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "tb_student_professor")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfessor implements GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student", referencedColumnName = "id")
    private User student;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "professor", referencedColumnName = "id")
    private User professor;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Enumerated
    private StudentTeacherApproved approved;

}
