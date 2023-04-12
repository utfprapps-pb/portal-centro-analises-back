package com.portal.centro.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Parameter description is required.")
    private String description;

    @NotNull
    private LocalDateTime date;

    /*
    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    private Solicitation solicitation;
    */

    @NotNull(message = "Parameter price is required.")
    private Float price;

    @NotNull(message = "Parameter amountHours is required.")
    private Integer amountHours;

    @NotNull(message = "Parameter amountSamples is required.")
    private Integer amountSamples;

}
