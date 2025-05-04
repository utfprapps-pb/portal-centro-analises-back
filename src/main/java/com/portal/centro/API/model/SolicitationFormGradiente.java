package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation_form_gradiente")
public class SolicitationFormGradiente extends IModel {

    @ManyToOne
    @JoinColumn(name = "form_id")
    @JsonIgnoreProperties(value = "grandientes")
    private SolicitationForm form;

    @Immutable
    @Column(name = "index")
    private Integer index;

    @Column(name = "tempo")
    private BigDecimal tempo;

    @Column(name = "fluxo")
    private BigDecimal fluxo;

    @Column(name = "solvente_a")
    private BigDecimal solventeA;

    @Column(name = "solvente_b")
    private BigDecimal solventeB;
}
