package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation_sample_analysis")
public class SolicitationAmostraAnalise extends IModel {

    @ManyToOne
    @JoinColumn(name = "sample_id")
    @JsonIgnoreProperties(value = "analises", allowSetters = true)
    private SolicitationAmostra amostra;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataini;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime datafin;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

}
