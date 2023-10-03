package com.portal.centro.API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Setter
    @Getter
    private String code;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "validate_at")
    private LocalDateTime validateAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
