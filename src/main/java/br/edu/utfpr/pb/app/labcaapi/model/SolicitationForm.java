package br.edu.utfpr.pb.app.labcaapi.model;

import br.edu.utfpr.pb.app.labcaapi.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
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
    @Column(name = "pickup")
    private RetiradaAmostra retirada = RetiradaAmostra.FALSE;

    @Column(name = "citation")
    private Boolean citacao = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id")
    @OrderColumn(name = "index")
    private List<SolicitationAmostra> amostras = new ArrayList<>();

    // AA
    @Column(name = "methodology_description")
    private String methodologyDescription;

    @Column(name = "concentration_limits")
    private String limitesConcentracao;

    @Enumerated
    @Column(name = "oven")
    private AAForno forno;

    @Column(name = "elements")
    private String elementos;

    @Column(name = "concentration_curve")
    private String curvaConcentracao;

    // CLAE
    @Column(name = "column_data")
    private String coluna;

    @Column(name = "flow")
    private BigDecimal fluxo;

    @Column(name = "analysis_time")
    private BigDecimal tempoAnalise;

    @Column(name = "injected_volume")
    private BigDecimal volumeInjetado;

    @Column(name = "column_oven_temperature")
    private BigDecimal temperaturaFornoColuna;

    @Column(name = "uses_pda")
    private Boolean utilizaPDA = false;

    @Column(name = "wave_length_channel_1")
    private BigDecimal compOndaCanal1;

    @Column(name = "wave_length_channel_2")
    private BigDecimal compOndaCanal2;

    @Enumerated
    @Column(name = "elution_mode")
    private CLAEModoEluicao modoEluicao;

    @Column(name = "mobile_phase_composition")
    private String composicaoFaseMovel;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id")
    @OrderColumn(name = "index")
    private List<SolicitationFormGradiente> gradientes = new ArrayList<>();

    // COR
    @Column(name = "location_med")
    private String locationMed;

    @Enumerated
    @Column(name = "reading_type")
    private CORTipoLeitura tipoLeitura;

    // DRX
    @Enumerated
    @Column(name = "analysis_mode")
    private DRXModoAnalise modoAnalise = DRXModoAnalise.CN;

    // FTMIR
    @Enumerated
    @Column(name = "service")
    private FTMIRServico servico;

    @Column(name = "scan_range")
    private String faixaVarredura;

    @Column(name = "resolution")
    private Long resolucao;

    @Column(name = "scans")
    private Long scans;

    @Enumerated
    @Column(name = "spectrum_records")
    private FTMIRRegistroEspectro registrosEspectos;

    @Enumerated
    @Column(name = "characteristic")
    private FTMIREstado caracteristica;

    @Column(name = "solvent")
    private String solvente;

}
