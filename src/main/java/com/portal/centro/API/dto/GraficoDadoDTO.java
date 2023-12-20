package com.portal.centro.API.dto;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraficoDadoDTO {

    private List<Long> data;
    private List<String> backgroundColor = Arrays.asList("#0059b2", "#2e96ff", "ffc24c", "#ff9f0e", "#f38200", "#2abfde", "#1f94ad", "#bd2c38", "#ff3143", "#ff8282");
}
