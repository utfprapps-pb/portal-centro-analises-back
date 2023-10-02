package com.portal.centro.API.model;

import com.portal.centro.API.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Parameter value is required.")
    private BigDecimal value;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Parameter user is required.")
    private User user;

    @Column(name = "old_balance")
    private BigDecimal oldBalance;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @ManyToOne
    @JoinColumn(name = "solicitation")
    private Solicitation solicitation;

    @NotNull(message = "Parameter type is required.")
    @Enumerated
    private TransactionType type;

    @Column(name = "description")
    private String description;

}
