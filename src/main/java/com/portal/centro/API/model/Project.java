package com.portal.centro.API.model;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_project")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends IModel {

    @NotNull()
    @Size(min = 4, max = 255)
    private String description;

    @NotNull()
    @Size(min = 4, max = 255)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "project_student",
            joinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private List<User> students;
}
