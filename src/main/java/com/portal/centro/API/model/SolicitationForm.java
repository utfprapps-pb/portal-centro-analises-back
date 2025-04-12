package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.portal.centro.API.enums.*;
import com.portal.centro.API.generic.base.IModel;
import com.portal.centro.API.generic.base.IModelCrud;
import com.portal.centro.API.generic.serialization.OnlyIdSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation_form")
public class SolicitationForm extends IModel {

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    @JsonIgnoreProperties(value = "form", allowSetters = true)
    private Solicitation solicitation;

    @Enumerated
    @Column(name = "retirada")
    private RetiradaAmostra retirada = RetiradaAmostra.FALSE;

    @Column(name = "citacao")
    private Boolean citacao = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id")
    @OrderColumn(name = "index")
    private List<SolicitationAmostra> amostras = new ArrayList<>();

    // AA
    @Column(name = "methodology_description")
    private String methodologyDescription;

    @Column(name = "limites_concentracao")
    private String limitesConcentracao;

    @Enumerated
    @Column(name = "forno")
    private AAForno forno;

    @Column(name = "elementos")
    private String elementos;

    @Column(name = "curva_concentracao")
    private String curvaConcentracao;

    // CLAE
    @Column(name = "coluna")
    private String coluna;

    @Column(name = "fluxo")
    private BigDecimal fluxo;

    @Column(name = "tempo_analise")
    private BigDecimal tempoAnalise;

    @Column(name = "volume_injetado")
    private BigDecimal volumeInjetado;

    @Column(name = "temperatura_forno_coluna")
    private BigDecimal temperaturaFornoColuna;

    @Column(name = "utilizapda")
    private Boolean utilizaPDA = false;

    @Column(name = "comp_onda_canal_1")
    private BigDecimal compOndaCanal1;

    @Column(name = "comp_onda_canal_2")
    private BigDecimal compOndaCanal2;

    @Enumerated
    @Column(name = "modo_eluicao")
    private CLAEModoEluicao modoEluicao;

    @Column(name = "composicao_fase_movel")
    private String composicaoFaseMovel;

    @Column(name = "condicoes_gradiente")
    private String condicoesGradiente;

    // COR
    @Column(name = "location_med")
    private String locationMed;

    @Enumerated
    @Column(name = "tipo_leitura")
    private CORTipoLeitura tipoLeitura;

    // DRX
    @Enumerated
    @Column(name = "modoAnalise")
    private DRXModoAnalise modoAnalise = DRXModoAnalise.CN;

    // FTMIR
    @Enumerated
    @Column(name = "servico")
    private FTMIRServico servico;

    @Column(name = "faixa_varredura")
    private Long faixaVarredura;

    @Column(name = "resolucao")
    private Long resolucao;

    @Enumerated
    @Column(name = "registros_espectos")
    private FTMIRRegistroEspectro registrosEspectos;

    @Enumerated
    @Column(name = "caracteristica")
    private FTMIREstado caracteristica;

    @Column(name = "solvente")
    private String solvente;

}
