package br.edu.utfpr.pb.app.labcaapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecoverPasswordDTO {

    @NotNull
    private String email;
    @NotNull
    private Integer code;
    @NotNull
    private String newPassword;

}
