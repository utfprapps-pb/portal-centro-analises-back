package com.portal.centro.API.dto;

import com.portal.centro.API.enums.SolicitationStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitationRequestDto {

    @NotNull
    private Long id;

    @Enumerated
    private SolicitationStatus status;

    private boolean approved;

    @Size(max = 2048)
    private String observation;

    private LocalDateTime scheduleDate;
}
