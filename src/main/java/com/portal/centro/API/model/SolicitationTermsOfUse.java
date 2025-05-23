package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "tb_solicitation_termsofuse")
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitationTermsOfUse extends IModel {

    @ManyToOne
    @JoinColumn(name = "termsofuse_id")
    private TermsOfUse termofuse;

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    @JsonIgnoreProperties(value = "termsOfUses", allowSetters = true)
    private Solicitation solicitation;

    private boolean accepted;

}
