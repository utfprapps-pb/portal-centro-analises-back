package br.edu.utfpr.pb.app.labcaapi.dto;

import br.edu.utfpr.pb.app.labcaapi.enums.SolicitationStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitationResponseDto {

    @NotNull
    private Long id;

    @Enumerated
    private SolicitationStatus status;

    @Size(max = 500)
    private String reason;

    private LocalDateTime data;

}
