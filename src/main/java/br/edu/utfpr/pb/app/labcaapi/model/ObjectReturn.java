package br.edu.utfpr.pb.app.labcaapi.model;

import lombok.Data;

@Data
public class ObjectReturn {

    private String message;
    private Object object;

    public ObjectReturn(String message) {
        this.message = message;
    }
}
