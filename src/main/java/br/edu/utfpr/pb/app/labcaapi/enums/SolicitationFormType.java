package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum SolicitationFormType {
    AA("[AA] Espectroscopia de Absorção Atômica"),
    AW("[AW] Atividade de Água"),
    CLAE("[CLAE] Cromatografia Líquida de Alta Eficiência (HPLC)"),
    COR("[COR] Análise Colorímetrica"),
    DRX("[DRX] Difratômetro de Raios X"),
    DSC("[DSC] Calorimetria Diferencial de Varredura"),
    FTMIR("[FT-MIR] Espectroscopia no Infravermelho Médio com Transformada de Fourier"),
    MEV("[MEV] Miscropia Eletrônica de Varredura"),
    TGADTA("[TGA] Termogravimetria | [DTA] Análise Térmica Diferencial");

    private final String content;

    SolicitationFormType(String content) {
        this.content = content;
    }

}
