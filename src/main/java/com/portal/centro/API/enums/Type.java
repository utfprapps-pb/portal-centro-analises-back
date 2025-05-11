package com.portal.centro.API.enums;

public enum Type {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_PROFESSOR("ROLE_PROFESSOR"),
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_PARTNER("ROLE_PARTNER"),
    ROLE_EXTERNAL("ROLE_EXTERNAL");

    private String content;

    Type(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String toReport() {
        return switch (content) {
            case "ROLE_ADMIN" -> "Administrador";
            case "ROLE_PROFESSOR" -> "Professor";
            case "ROLE_STUDENT" -> "Aluno";
            case "ROLE_PARTNER" -> "Parceiro";
            case "ROLE_EXTERNAL" -> "Externo";
            default -> "Desconhecido";
        };
    }
}
