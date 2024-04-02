package com.portal.centro.API.model;

import com.portal.centro.API.enums.SolicitationProjectNature;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.TypeUser;
import com.portal.centro.API.generic.base.IModel;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.LastModifiedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation")
public class Solicitation extends IModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "user_updated_id")
    private User updatedBy;

    @Enumerated
    private SolicitationStatus status;

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
    @Column(name = "methodology_description")
    private String methodologyDescription;

    @NotNull(message = "Form must not be null")

    @Type(JsonType.class)
    @Column(name = "form", columnDefinition = "jsonb")
    private String form;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Project nature must not be null")
    @Column(name = "project_nature")
    private SolicitationProjectNature projectNature;

    /**
     * When the user needs a project nature that isn't in the enum SolicitationProjectNature.
     */
    @Column(name = "other_project_nature")
    private String otherProjectNature;

    @Column(name = "schedule_date")
    private LocalDateTime scheduleDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull(message = "Parameter price is required.")
    private BigDecimal price;

    @NotNull(message = "Parameter amountHours is required.")
    private BigDecimal amountHours;

    @NotNull(message = "Parameter amountSamples is required.")
    private Integer amountSamples;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "technical_report_id")
    private List<SolicitationAttachments> solicitationAttachments; //adicionar coluna nova no banco

    @ManyToOne
    @JoinColumn(name = "analysis_id")
    private Analysis analysis;

//    @Column(name = "rejection_reason")
//    @Size(max = 500)
//    private String rejectionReason;
}
