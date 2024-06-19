package com.portal.centro.API.enums;

public enum StudentTeacherApproved {

    ACEITO("Aceito"),
    PENDENTE("Pendente"),
    RECUSADO("Recusado");

    private final String content;

    StudentTeacherApproved(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
