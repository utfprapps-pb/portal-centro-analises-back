package com.portal.centro.API.model;

import com.portal.centro.API.enums.SolicitationProjectNature;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.TypeUser;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "solicitation")
public class Solicitation extends IModel {

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User createdBy;

    @NotNull(message = "Equipment must not be null")
    @OneToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @NotNull(message = "Project must not be null")
    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @NotNull(message = "Description must not be null")
    @NotBlank(message = "Description must not be empty")
    private String description;

    @NotNull(message = "Fields must not be null")
    private String fields;

    @Enumerated
    private SolicitationStatus status;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Project nature must not be null")
    private SolicitationProjectNature projectNature;

    private String otherProjectNature;

    @Enumerated(value = EnumType.STRING)
    private TypeUser typeUser;

    private BigDecimal value;

    @Column(name = "rejection_reason")
    @Size(max = 500)
    private String rejectionReason;

    @Column(name = "scheduledate")
    private LocalDateTime scheduleDate;

}
