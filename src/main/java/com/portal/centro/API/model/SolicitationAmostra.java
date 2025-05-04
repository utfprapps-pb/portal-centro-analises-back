package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.enums.DRXStep;
import com.portal.centro.API.enums.EnumBoolean;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation_amostra")
public class SolicitationAmostra extends IModel {

    @ManyToOne
    @JoinColumn(name = "form_id")
    @JsonIgnoreProperties(value = "amostras")
    private SolicitationForm form;

    @Immutable
    @Column(name = "index")
    private Integer index;

    @Column(name = "identification")
    private String identification;

    @Column(name = "description")
    private String description;

    @Column(name = "leituras")
    private Long leituras;

    @Column(name = "concluida")
    private Boolean concluida;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "amostra_id")
    private List<SolicitationAmostraAnalise> analises = new ArrayList<>();

    @Column(name = "composicao")
    private String composicao;

    @Column(name = "toxic")
    private String toxic;

    @Column(name = "natureza_amostra")
    private String naturezaAmostra;

    @Column(name = "descarte_organico")
    private String descarteOrganico;

    @Column(name = "descarte_organico_outro")
    private String descarteOrganicoOutro;

    @Column(name = "descarte_inorganico")
    private String descarteInorganico;

    @Column(name = "descarte_inorganico_outro")
    private String descarteInorganicoOutro;

    @Column(name = "descarte_usuario")
    private String descarteUsuario;

    @Column(name = "descarte_usuario_outro")
    private String descarteUsuarioOutro;

    // DRX
    @Column(name = "faixa_varredura")
    private Long faixaVarredura;

    @Enumerated
    @Column(name = "step")
    private DRXStep step = DRXStep._002;

    @Column(name = "velocidade_varredura")
    private Long velocidadeVarredura;

    @Column(name = "tempo_passo")
    private Long tempoPasso;

    // DSC - TGADTA

    @Column(name = "expande")
    private Boolean expande;

    @Enumerated
    @Column(name = "libera_gas")
    private EnumBoolean liberaGas;

    @Column(name = "gas_liberado")
    private String gasLiberado;

    @Column(name = "massa")
    private Long massa;

    @Column(name = "tecnica")
    private String tecnica;

    @Column(name = "atmosfera")
    private String atmosfera;

    @Column(name = "fluxo_gas")
    private Long fluxoGas;

    @Column(name = "taxa_aquecimento")
    private BigDecimal taxaAquecimento;

    @Column(name = "intervalo_temperatura")
    private String intervaloTemperatura;

    // MEV
    @Column(name = "tipo_amostra")
    private String tipoAmostra;

    @Column(name = "objetivo")
    private String objetivo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "amostra", allowSetters = true)
    private List<SolicitationAmostraFoto> fotos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelo_microscopia")
    private Attachment modeloMicroscopia;

}
