package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.enums.SolicitationFormType;
import com.portal.centro.API.enums.SolicitationProjectNature;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.base.IModelCrud;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation")
public class Solicitation extends IModelCrud {

    @ManyToOne
    @JoinColumn(name = "responsavel_id", updatable = false)
    private User responsavel;

    @Immutable
    @Enumerated
    @Column(name = "status")
    private SolicitationStatus status;

    @ManyToOne
    @NotNull(message = "Project must not be null")
    @JoinColumn(name = "project_id", updatable = false)
    private Project project;

    @ManyToOne
    @NotNull(message = "Form must not be null")
    @JoinColumn(name = "form_id", updatable = false)
    private SolicitationForm form;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Solicitation Type must not be null")
    @Column(name = "solicitation_type", updatable = false)
    private SolicitationFormType solicitationType;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Project nature must not be null")
    @Column(name = "project_nature", updatable = false)
    private SolicitationProjectNature projectNature;

    @Column(name = "other_project_nature", updatable = false)
    private String otherProjectNature;

    private Integer amountSamples;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitation_id")
    @JsonIgnoreProperties(value = "solicitation", allowSetters = true)
    private List<SolicitationTermsOfUse> termsOfUses;

    private String observation;

}
