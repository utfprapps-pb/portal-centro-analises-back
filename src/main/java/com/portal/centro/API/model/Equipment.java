package com.portal.centro.API.model;

import com.portal.centro.API.enums.StatusInactiveActive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "equipment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @Column(name="value_hour_utfpr")
    private BigDecimal valueHourUtfpr;

    @Column(name="value_hour_partner")
    private BigDecimal valueHourPartner;

    @Column(name="value_hour_pf_pj")
    private BigDecimal valueHourPfPj;

    @Column(name="value_sample_utfpr")
    private BigDecimal valueSampleUtfpr;

    @Column(name="value_sample_partner")
    private BigDecimal valueSamplePartner;

    @Column(name="value_sample_pf_pj")
    private BigDecimal valueSamplePfPj;

    @Column(name="form")
    private String form;

    @Column(name="short_name")
    private String shortName;

    private StatusInactiveActive status;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private String createdby;

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedby;
}
