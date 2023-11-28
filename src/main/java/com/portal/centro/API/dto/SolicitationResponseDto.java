package com.portal.centro.API.dto;

import com.portal.centro.API.enums.SolicitationStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitationResponseDto {

    @NotNull
    private Long id;

    @Enumerated
    private SolicitationStatus status;

    private LocalDateTime data;

}
