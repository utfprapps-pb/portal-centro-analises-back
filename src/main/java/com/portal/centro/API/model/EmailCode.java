package com.portal.centro.API.model;

import com.portal.centro.API.generic.crud.GenericModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity(name = "tb_email_code")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCode implements GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "validate_at")
    private LocalDateTime validateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
