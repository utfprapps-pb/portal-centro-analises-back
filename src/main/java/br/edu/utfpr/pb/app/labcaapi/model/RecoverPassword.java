package br.edu.utfpr.pb.app.labcaapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPassword {

    private String email;
    private Integer code;
    private LocalDateTime dateTime;

}