package br.edu.utfpr.pb.app.labcaapi.dto;

import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {

    public UserLoginDTO(User user) {
        this.id = user.getId();
        this.displayName = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    private Long id;
    private String displayName;
    private String email;
    private Type role;

}
