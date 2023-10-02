package com.portal.centro.API.model;

import com.portal.centro.API.validations.user.UserUniqueConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.cfg.context.Cascadable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "student_teacher")
public class StudentTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "student", referencedColumnName = "id")
    private User student;

    @NotNull
    @OneToOne
    @JoinColumn(name = "teacher", referencedColumnName = "id")
    private User teacher;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private Boolean aproved;



}
