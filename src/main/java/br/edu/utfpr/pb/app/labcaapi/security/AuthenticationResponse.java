package br.edu.utfpr.pb.app.labcaapi.security;

import br.edu.utfpr.pb.app.labcaapi.dto.UserLoginDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private String token;
    private UserLoginDTO user;

}
