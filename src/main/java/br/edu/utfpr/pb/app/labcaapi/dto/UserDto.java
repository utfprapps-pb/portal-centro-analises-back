package br.edu.utfpr.pb.app.labcaapi.dto;

import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private Type role;

    @Enumerated
    private UserType type = UserType.PF;

    @Size(min = 4, max = 255)
    private String name;

    @Email
    private String email;

    @Enumerated
    private StatusInactiveActive status;

    private BigDecimal balance;

    private String raSiape;

    private String cpfCnpj;

}
