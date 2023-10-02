package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portal.centro.API.enums.Action;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "permission")
@Entity
public class Permission extends IModel implements GrantedAuthority {

    @Column(name = "description", length = 50, nullable = false)
    @JsonIgnore
    @Setter
    @Getter
    @NotNull(message = "Description must not be null!")
    @NotBlank(message = "Description must not be empty!")
    private String description;

    @Enumerated
    @Getter
    @NotNull(message = "Action must not be null!")
    @NotBlank(message = "Action must not be empty!")
    private Action action;

    @Override
    public String getAuthority() {
        return description;
    }
}
