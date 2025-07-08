package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.enums.DRXStep;
import br.edu.utfpr.pb.app.labcaapi.enums.EnumBoolean;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
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
@Entity(name = "tb_solicitation_sample")
public class SolicitationAmostra extends IModel {

    @ManyToOne()
    @JoinColumn(name = "form_id")
    @JsonIgnoreProperties(value = {"gradientes", "amostras"}, allowSetters = true)
    private SolicitationForm form;

    @Immutable
    @Column(name = "index")
    private Integer index;

    @Column(name = "identification")
    private String identification;

    @Column(name = "description")
    private String description;

    @Column(name = "readings")
    private Long leituras;

    @Column(name = "completed")
    private Boolean concluida;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sample_id")
    private List<SolicitationAmostraAnalise> analises = new ArrayList<>();

    @Column(name = "composition")
    private String composicao;

    @Column(name = "toxic")
    private String toxic;

    @Column(name = "sample_nature")
    private String naturezaAmostra;

    @Column(name = "organic_disposal")
    private String descarteOrganico;

    @Column(name = "organic_disposal_other")
    private String descarteOrganicoOutro;

    @Column(name = "inorganic_disposal")
    private String descarteInorganico;

    @Column(name = "inorganic_disposal_other")
    private String descarteInorganicoOutro;

    @Column(name = "user_disposal")
    private String descarteUsuario;

    @Column(name = "user_disposal_other")
    private String descarteUsuarioOutro;

    // DRX
    @Column(name = "scan_range")
    private Long faixaVarredura;

    @Enumerated
    @Column(name = "step")
    private DRXStep step = DRXStep._002;

    @Column(name = "scan_speed")
    private Long velocidadeVarredura;

    @Column(name = "step_time")
    private Long tempoPasso;

    // DSC - TGADTA

    @Column(name = "expand")
    private Boolean expande;

    @Enumerated
    @Column(name = "gas_release")
    private EnumBoolean liberaGas;

    @Column(name = "released_gas")
    private String gasLiberado;

    @Column(name = "mass")
    private Long massa;

    @Column(name = "technique")
    private String tecnica;

    @Column(name = "atmosphere")
    private String atmosfera;

    @Column(name = "gas_flow")
    private Long fluxoGas;

    @Column(name = "heating_rate")
    private BigDecimal taxaAquecimento;

    @Column(name = "temperature_range")
    private String intervaloTemperatura;

    // MEV
    @Column(name = "sample_type")
    private String tipoAmostra;

    @Column(name = "objective")
    private String objetivo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "sample", allowSetters = true)
    private List<SolicitationAmostraFoto> fotos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "microscopy_model")
    private Attachment modeloMicroscopia;

}
