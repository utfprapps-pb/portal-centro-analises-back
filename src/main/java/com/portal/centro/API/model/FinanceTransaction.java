package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.enums.FinanceState;
import com.portal.centro.API.generic.base.IModel;
import com.portal.centro.API.generic.base.IModelCrud;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
