package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "tb_finance_transaction")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceTransaction extends IModel {

    @ManyToOne
    @JoinColumn(name = "finance_id")
    @JsonIgnoreProperties(value = "details", allowSetters = true)
    private Finance finance;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User usuario;

    @Column(name = "value")
    private BigDecimal value;


}
