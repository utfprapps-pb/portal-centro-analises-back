package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation_amostra_analise")
public class SolicitationAmostraAnalise extends IModel {

    @ManyToOne
    @JoinColumn(name = "amostra_id")
    @JsonIgnoreProperties(value = "analises", allowSetters = true)
    private SolicitationAmostra amostra;

    @Column(name = "dataini")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataini;

    @Column(name = "datafin")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime datafin;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

}
