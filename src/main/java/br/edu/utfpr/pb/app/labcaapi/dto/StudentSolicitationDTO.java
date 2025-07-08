package br.edu.utfpr.pb.app.labcaapi.dto;

import br.edu.utfpr.pb.app.labcaapi.model.User;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class StudentSolicitationDTO {

    @NotNull
    private User solicitatedTo;

}
