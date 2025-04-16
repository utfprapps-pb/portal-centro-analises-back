package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation_amostra_foto")
public class SolicitationAmostraFoto extends IModel {

    @ManyToOne
    @JoinColumn(name = "amostra_id")
    @JsonIgnoreProperties(value = "analises")
    private SolicitationAmostra amostra;

    @Column(name = "aproximacoes")
    private Long aproximacoes;

    @Column(name = "qtd_fotos_aproximacao")
    private Long qtdFotosAproximacao;

}
