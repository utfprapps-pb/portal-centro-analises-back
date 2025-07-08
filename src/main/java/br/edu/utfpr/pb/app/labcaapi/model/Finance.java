package br.edu.utfpr.pb.app.labcaapi.model;

import br.edu.utfpr.pb.app.labcaapi.enums.FinanceState;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModelCrud;
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

    @Column(name = "observation")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "responsible_id", updatable = false)
    private User responsavel;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User pagador;

    @Column(name = "update_balance")
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
