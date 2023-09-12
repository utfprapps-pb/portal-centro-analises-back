package com.portal.centro.API.model;

import com.portal.centro.API.enums.StatusInactiveActive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity(name = "equipment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private StatusInactiveActive status;
}
