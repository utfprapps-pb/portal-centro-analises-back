package com.portal.centro.API.dto;

import com.portal.centro.API.enums.SolicitationStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SolicitationResponseDto {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private SolicitationStatus status;

    private String reason;
}
