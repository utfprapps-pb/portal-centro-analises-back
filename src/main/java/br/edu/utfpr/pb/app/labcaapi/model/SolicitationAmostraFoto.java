package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
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
@Entity(name = "tb_solicitation_sample_photo")
public class SolicitationAmostraFoto extends IModel {

    @ManyToOne
    @JoinColumn(name = "sample_id")
    @JsonIgnoreProperties(value = "analises")
    private SolicitationAmostra amostra;

    @Column(name = "approximations")
    private Long aproximacoes;

    @Column(name = "total_approximations_photos")
    private Long qtdFotosAproximacao;

}
