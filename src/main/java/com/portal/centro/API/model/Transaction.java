package com.portal.centro.API.model;

import com.portal.centro.API.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

@Entity(name = "tb_transaction")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends IModel {

    @NotNull(message = "Parameter value is required.")
    @Column(name = "total_value")
    private BigDecimal totalValue;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User created_by;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Parameter user is required.")
    private User user;

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    private Solicitation solicitation;

    @Column(name = "description")
    private String description;

}
