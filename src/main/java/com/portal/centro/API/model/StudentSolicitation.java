package com.portal.centro.API.model;

import com.portal.centro.API.enums.StudentSolicitationStatus;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "student_solicitation")
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Enumerated
    @NotNull
    private StudentSolicitationStatus status;

}
