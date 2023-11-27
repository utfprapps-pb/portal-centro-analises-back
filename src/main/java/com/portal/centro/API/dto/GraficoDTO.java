package com.portal.centro.API.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraficoDTO {

    private String titulo;
    private List<GraficoDadoDTO> dados;

}
