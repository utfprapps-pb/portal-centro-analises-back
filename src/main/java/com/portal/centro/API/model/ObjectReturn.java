package com.portal.centro.API.model;

import lombok.Data;

@Data
public class ObjectReturn {

    private String message;
    private Object object;

    public ObjectReturn(String message) {
        this.message = message;
    }
}
