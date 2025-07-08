package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "tb_solicitation_terms_of_use")
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitationTermsOfUse extends IModel {

    @ManyToOne
    @JoinColumn(name = "terms_of_use_id")
    private TermsOfUse termofuse;

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    @JsonIgnoreProperties(value = "termsOfUses", allowSetters = true)
    private Solicitation solicitation;

    private boolean accepted;

}
