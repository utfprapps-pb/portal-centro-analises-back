package com.portal.centro.API.dto;

import jakarta.persistence.Tuple;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraficoDadoDTO {

    private long id;
    private String label;
    private long value;

}
