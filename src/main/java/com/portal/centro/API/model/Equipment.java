package com.portal.centro.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
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

    @NotNull
    private Double value_hour_utfpr;

    @NotNull
    private Double value_hour_partner;

    @NotNull
    private Double value_hour_pf_pj;

    @NotNull
    private Double value_sample_utfpr;

    @NotNull
    private Double value_sample_partner;

    @NotNull
    private Double value_sample_pf_pj;

}
