package com.portal.centro.API.model;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity(name = "tb_user_balance")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "set_unique_user", columnNames = "user_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalance extends IModel {

    @ManyToOne
    private User user;

    private BigDecimal balance;

    @Column(name = "negative_limit")
    private BigDecimal negativeLimit;

}
