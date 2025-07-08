package br.edu.utfpr.pb.app.labcaapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class EmailDto {
    @Email
    private String emailTo;

    @Null
    private String subject;
    @Null
    private String subjectBody;
    @Null
    private String contentBody;
}
