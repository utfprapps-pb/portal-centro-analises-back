package com.portal.centro.API.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GraficoDTO {

    private String titulo;
    private List<String> labels;
    private List<GraficoDadoDTO> datasets;

}