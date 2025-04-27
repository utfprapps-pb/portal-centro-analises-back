package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.generic.base.IModelCrud;
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
    @JsonIgnoreProperties(value = "details", allowSetters = true)
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
