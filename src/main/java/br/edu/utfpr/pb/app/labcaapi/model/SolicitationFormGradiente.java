package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_solicitation_form_gradient")
public class SolicitationFormGradiente extends IModel {

    @ManyToOne
    @JoinColumn(name = "form_id")
    @JsonIgnoreProperties(value = {"gradientes", "amostras"}, allowSetters = true)
    @JsonIdentityReference(alwaysAsId = true)
    private SolicitationForm form;

    @Immutable
    @Column(name = "index")
    private Integer index;

    @Column(name = "time")
    private BigDecimal tempo;

    @Column(name = "flow")
    private BigDecimal fluxo;

    @Column(name = "solvent_a")
    private BigDecimal solventeA;

    @Column(name = "solvent_b")
    private BigDecimal solventeB;
}
