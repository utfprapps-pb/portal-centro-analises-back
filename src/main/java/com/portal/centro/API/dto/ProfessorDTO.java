package com.portal.centro.API.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfessorDTO {
    Long id;
    String name;
    String email;
}
