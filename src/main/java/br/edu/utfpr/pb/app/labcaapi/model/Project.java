package br.edu.utfpr.pb.app.labcaapi.model;

import br.edu.utfpr.pb.app.labcaapi.enums.SolicitationProjectNature;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private List<ProjectStudent> students = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Project nature must not be null")
    @Column(name = "project_nature")
    private SolicitationProjectNature projectNature;

    /**
     * Quando a solicitação vem de um projeto que não está no ENUM SolicitationProjectNature.
     */
    @Column(name = "other_project_nature")
    private String otherProjectNature;

}
