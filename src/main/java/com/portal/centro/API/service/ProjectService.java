package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.ProjectRepository;
import com.portal.centro.API.security.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProjectService extends GenericService<Project, Long> {

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final AuthService authService;

    public ProjectService(UserService userService, ProjectRepository projectRepository, AuthService authService) {
        super(projectRepository);

        this.projectRepository = projectRepository;
        this.userService = userService;
        this.authService = authService;
    }

    public Project linkUserToProject(Long studentId, Project linkTo) {
        Project project = projectRepository.findById(linkTo.getId()).orElse(null);

        if (project != null) {
            User student = userService.findOneById(studentId);
            project.getStudents().add(student);
            return projectRepository.save(project);
        } else {
            return null;
        }
    }

    public List<Project> getAllProjects() {
        User user = userService.findSelfUser();
        List<Project> projects;

        if (!Objects.equals(user.getRole(), Type.ROLE_ADMIN)) {
            projects = projectRepository.findAll();
        } else {
            projects = projectRepository.findAllByUserEqualsOrStudentsContains(user, user);

            for (Project project : projects) {
                if (ObjectUtils.isNotEmpty(project.getStudents())) {
                    project.setStudents(
                            project.getStudents()
                                    .stream()
                                    .filter(it -> it.getId().equals(user.getId()))
                                    .toList()
                    );
                }
            }
        }

        return projects;
    }

    /**
     * Irá setar o usuário apenas quando vindo de um professor.
     * Quando cadastrado pelo Lab o usuário deverá vir na requisição.
     */
    @Override
    public Project save(Project project) throws Exception {
        User user = authService.findLoggedUser();
        if (user.getRole().equals(Type.ROLE_PROFESSOR)) {
            project.setUser(user);
        }
        try {
            projectRepository.save(project);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return project;
    }

    @Override
    public Page<Project> page(PageRequest pageRequest) {
        User user = userService.findSelfUser();
        return switch (user.getRole()) {
            case ROLE_ADMIN -> super.page(pageRequest);
            case ROLE_PROFESSOR -> projectRepository.findAllByUserEqualsOrStudentsContains(user, user, pageRequest);
            default -> throw new ValidationException("Você não possui permissão para acessar este recurso.");
        };
    }
}
