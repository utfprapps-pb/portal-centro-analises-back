package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum SolicitationStatus {
    AWAITING_RESPONSIBLE_CONFIRMATION("Aguardando confirmação do responsável"),
    AWAITING_CORRECTION("Aguardando correção"),
    AWAITING_LAB_CONFIRMATION("Aguardando confirmação do laboratório"),
    AWAITING_SAMPLE("Aguardando amostra"),
    AWAITING_ANALYSIS("Aguardando análise"),
    ANALYZING("Analisando"),
    AWAITING_PAYMENT("Aguardando pagamento"),
    REJECTED("Recusado"),
    COMPLETED("Concluído"),
    UNEXPECTED_BLOCK("Bloqueio Imprevisto"),
    TECHNICAL_BREAK("Pausa Técnica");

    private final String content;

    SolicitationStatus(String content) {
        this.content = content;
    }

}
