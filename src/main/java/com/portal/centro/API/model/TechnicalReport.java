package com.portal.centro.API.model;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "technical_report")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalReport extends IModel {

    @NotNull(message = "Parameter description is required.")
    private String description;

    @NotNull
    private LocalDateTime date;

    @ManyToOne
    private Solicitation solicitation;

    @NotNull(message = "Parameter price is required.")
    private Float price;

    @NotNull(message = "Parameter amountHours is required.")
    private Integer amountHours;

    @NotNull(message = "Parameter amountSamples is required.")
    private Integer amountSamples;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "technical_report_id")
    private List<MultiPartFileList> multiPartFileLists; //adicionar coluna nova no banco

}
