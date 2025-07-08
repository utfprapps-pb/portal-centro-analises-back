package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModelCrud;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "tb_finance_detail")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceDetails extends IModelCrud {

    @ManyToOne
    @JoinColumn(name = "finance_id")
    @JsonIgnoreProperties(value = {"attachments", "details"})
    private Finance finance;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    @JsonIgnoreProperties(value = "analise", allowSetters = true)
    private Equipment equipment;

    @Column(name = "calculatebyhours")
    private Boolean calculateByHours;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "value")
    private BigDecimal value;

}
