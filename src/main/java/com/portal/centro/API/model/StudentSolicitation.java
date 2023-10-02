package com.portal.centro.API.model;

import com.portal.centro.API.enums.StudentSolicitationStatus;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "student_solicitation")
public class StudentSolicitation extends IModel {

    @ManyToOne
    @JoinColumn(name = "solicitated_by")
    @NotNull
    private User solicitatedBy;

    @ManyToOne
    @JoinColumn(name = "solicitated_to")
    @NotNull
    private User solicitatedTo;

    @ManyToOne
    @JoinColumn(name = "finished_by")
    private User finishedBy;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @Column(name = "creation_date")
    @NotNull
    private LocalDateTime creationDate;

    @Enumerated
    @NotNull
    private StudentSolicitationStatus status;

}
