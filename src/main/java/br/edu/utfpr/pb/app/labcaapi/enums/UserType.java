package br.edu.utfpr.pb.app.labcaapi.enums;

public enum UserType {

    PF("Pessoa Física"),
    PJ("Pessoa Jurídica");

    private final String content;

    UserType(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
