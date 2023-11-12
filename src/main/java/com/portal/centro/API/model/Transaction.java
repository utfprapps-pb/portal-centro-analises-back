package com.portal.centro.API.model;

import com.portal.centro.API.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Parameter value is required.")
    private BigDecimal value;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private String createdBy;

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
