package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.edu.utfpr.pb.app.labcaapi.enums.Action;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Table
@Entity(name = "tb_permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends IModel implements GrantedAuthority {

    @Column(name = "description", length = 50, nullable = false)
    @JsonIgnore
    @NotNull(message = "Description must not be null!")
    @NotBlank(message = "Description must not be empty!")
    private String description;

    @Enumerated
    @NotNull(message = "Action must not be null!")
    @NotBlank(message = "Action must not be empty!")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Override
    public String getAuthority() {
        return description;
    }
}
