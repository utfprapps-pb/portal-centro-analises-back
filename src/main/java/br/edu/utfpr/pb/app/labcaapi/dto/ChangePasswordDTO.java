package br.edu.utfpr.pb.app.labcaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {

  @NotNull
  @NotBlank
  @NotEmpty
  @Size(min = 6, max = 254, message = "Old Password must be between 6 and 254 characters long")
  private String oldPassword;

  @NotNull
  @NotBlank
  @NotEmpty
  @Size(min = 6, max = 254, message = "New Password must be between 6 and 254 characters long")
  private String newPassword;

}
