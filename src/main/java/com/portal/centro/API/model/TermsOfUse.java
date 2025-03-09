package com.portal.centro.API.model;

import com.portal.centro.API.enums.SolicitationFormType;
import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.base.IModel;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_termsofuse")
public class TermsOfUse extends IModel {

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "user_updated_id")
    private User updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "solicitation_type")
    private SolicitationFormType solicitationType;

    @Enumerated
    private StatusInactiveActive status;

    @NotNull(message = "Title must not be null")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Checkbox description must not be null")
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @NotNull(message = "Form must not be null")
    @Column(name = "form", columnDefinition = "text")
    private String form;

}
