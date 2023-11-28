package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.validations.user.UserUniqueConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "setuniqueemail", columnNames = "email")
})
@UserUniqueConstraint
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private Type role;

    @NotNull(message = "Parameter name is required.")
    @Size(min = 4, max = 255)
    private String name;

    @NotNull(message = "Parameter email is required.")
    @Email
    private String email;

    @NotNull(message = "Parameter password is required.")
    @Size(min = 6, max = 254)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private StatusInactiveActive status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    private BigDecimal balance;

    private String ra;

    private String siape;

    private String cpf;

    private String cnpj;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Override
    @Transient
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> list = new ArrayList<>();
//        list.addAll(this.permissions);
//        return list;

        List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.name()));
        // Você pode adicionar permissions aqui, se necessário
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Permission> permissions;

    @Override
    @Transient
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
