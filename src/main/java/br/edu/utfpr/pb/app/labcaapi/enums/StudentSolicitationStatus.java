package br.edu.utfpr.pb.app.labcaapi.enums;

public enum StudentSolicitationStatus {
    PENDING("Aguardando Confirmação"),
    APPROVED_ADVISOR("Aprovado pelo orientador"),
    APPROVED_LAB("Aprovado pelo laboratório"),
    REFUSED_ADVISOR("Recusado pelo orientador"),
    REFUSED_LAB("Recusado pelo laboratório");

    private String content;

    StudentSolicitationStatus(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
