package com.portal.centro.API.model;

import com.portal.centro.API.enums.SolicitationFormType;
import com.portal.centro.API.enums.SolicitationProjectNature;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.base.IModel;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
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

    /**
     * Quando criado por um aluno vai o professor orientador dele no momento da criação da solicitação
     */
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private User responsavel;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "user_updated_id")
    private User updatedBy;

    @Enumerated
    @Column(name = "status", updatable = false)
    private SolicitationStatus status;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne
    @NotNull(message = "Project must not be null")
    @JoinColumn(name = "project_id")
    private Project project;

    @NotNull(message = "Form must not be null")
    @Type(JsonType.class)
    @Column(name = "form", columnDefinition = "jsonb")
    private Object form;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Solicitation Type must not be null")
    @Column(name = "solicitation_type")
    private SolicitationFormType solicitationType;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Project nature must not be null")
    @Column(name = "project_nature")
    private SolicitationProjectNature projectNature;

    /**
     * Quando a solicitação vem de um projeto que não está no ENUM SolicitationProjectNature.
     */
    @Column(name = "other_project_nature")
    private String otherProjectNature;

    @Column(name = "schedule_date")
    private LocalDateTime scheduleDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private BigDecimal price;

    private BigDecimal amountHours;

    @NotNull(message = "Parameter amountSamples is required.")
    private Integer amountSamples;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitation_id")
    private List<SolicitationAttachments> solicitationAttachments; //adicionar coluna nova no banco

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitation_id")
    private List<SolicitationTermsOfUse> termsOfUses;

    private String observation;

    /**
     * Caso seja usuário externo o laboratório deverá marcar como pago para
     * liberar o relatório com o resultado da análise.
     */
    private boolean paid;

}
