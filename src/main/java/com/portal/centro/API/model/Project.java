package com.portal.centro.API.model;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project")
public class Project extends IModel {


    @NotNull()
    @Size(min = 4, max = 255)
    private String description;

    @NotNull()
    @Size(min = 4, max = 255)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToMany
    @JoinTable(name = "project_student",
            joinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private List<User> students;
}
