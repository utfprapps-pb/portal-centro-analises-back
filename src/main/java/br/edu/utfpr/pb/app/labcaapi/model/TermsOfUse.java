package br.edu.utfpr.pb.app.labcaapi.model;

import br.edu.utfpr.pb.app.labcaapi.enums.SolicitationFormType;
import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModelCrud;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_terms_of_use")
public class TermsOfUse extends IModelCrud {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "solicitation_type")
    private SolicitationFormType solicitationType;

    @Enumerated
    private StatusInactiveActive status;

    @NotNull(message = "Title must not be null")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Checkbox description must not be null")
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "form", columnDefinition = "text")
    private String form;

}
