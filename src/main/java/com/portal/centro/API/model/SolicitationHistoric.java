package com.portal.centro.API.model;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity(name = "tb_solicitation_historic")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitationHistoric extends IModel {

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    private Solicitation solicitation;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Enumerated
    private SolicitationStatus status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "observation")
    private String observation;
}


