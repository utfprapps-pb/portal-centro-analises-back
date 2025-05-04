package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.enums.FinanceState;
import com.portal.centro.API.generic.base.IModelCrud;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "tb_finance")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Finance extends IModelCrud {

    @Column(name = "description")
    private String description;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "responsavel_id", updatable = false)
    private User responsavel;

    @ManyToOne
    @JoinColumn(name = "pagador_id")
    private User pagador;

    @Column(name = "alterar_saldo")
    private Boolean alterarSaldo = true;

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    private Solicitation solicitation;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Enumerated
    @Column(name = "state")
    private FinanceState state;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "finance_id")
//    @JsonIgnoreProperties(value = "finance", allowSetters = true, allowGetters = true)
    private List<FinanceDetails> details;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "finance_id")
//    @JsonIgnoreProperties(value = "finance", allowSetters = true, allowGetters = true)
    private List<Attachment> attachments;
}
