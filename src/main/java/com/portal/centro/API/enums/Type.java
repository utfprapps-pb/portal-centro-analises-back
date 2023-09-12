package com.portal.centro.API.enums;

public enum Type {
    PROFESSOR("professor"),
    STUDENT("student"),
    EXTERNAL("external"),
    ADMIN("admin"),
//    TODO: ajustar para onde faz validação com o external, fazer o mesmo com partner, pois só irá
//            mudar na hora de calcular o preço, fora isso segue a mesma regra do external
    PARTNER("partner");

    private String content;

    Type (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
