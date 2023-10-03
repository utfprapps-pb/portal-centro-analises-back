package com.portal.centro.API.dto;

import com.portal.centro.API.model.Solicitation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalReportDTO {
    @NotNull(message = "Parameter description is required.")
    private String description;

    @NotNull
    private LocalDateTime date;

    private Solicitation solicitation;

    @NotNull(message = "Parameter price is required.")
    @NotBlank(message = "Price must not be empty.")
    private Float price;

    @NotNull(message = "Parameter amountHours is required.")
    @NotBlank(message = "AmountHours must not be empty.")
    private Integer amountHours;

    @NotNull(message = "Parameter amountSamples is required.")
    @NotBlank(message = "AmountSamples must not be empty.")
    private Integer amountSamples;

    private  String fileName;
    private  String contentType;
}
