package br.edu.utfpr.pb.app.labcaapi.dto;

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