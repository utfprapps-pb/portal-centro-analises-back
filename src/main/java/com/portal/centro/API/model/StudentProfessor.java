package com.portal.centro.API.model;

import com.portal.centro.API.generic.crud.GenericModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_student_professor")
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
    @OneToOne
    @JoinColumn(name = "student", referencedColumnName = "id")
    private User student;

    @NotNull
    @OneToOne
    @JoinColumn(name = "professor", referencedColumnName = "id")
    private User professor;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private Boolean approved;

}
