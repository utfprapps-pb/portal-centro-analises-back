package com.portal.centro.API.model;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "tb_equipment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment implements GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @Column(name="short_name")
    private String shortName;

    @Column(name="model")
    private String model;

    private StatusInactiveActive status;

    @Column(name="value_hour_utfpr")
    private BigDecimal valueHourUtfpr;

    @Column(name="value_hour_partner")
    private BigDecimal valueHourPartner;

    @Column(name="value_hour_external")
    private BigDecimal valueHourExternal;

    @Column(name="value_sample_utfpr")
    private BigDecimal valueSampleUtfpr;

    @Column(name="value_sample_partner")
    private BigDecimal valueSamplePartner;

    @Column(name="value_sample_external")
    private BigDecimal valueSampleExternal;

}
