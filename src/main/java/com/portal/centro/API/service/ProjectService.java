package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.ProjectRepository;
import com.portal.centro.API.security.AuthService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProjectService extends GenericService<Project, Long> {

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final AuthService authService;

    @PersistenceContext
    private EntityManager entityManager;


    public ProjectService(UserService userService, ProjectRepository projectRepository, AuthService authService) {
        super(projectRepository);

        this.projectRepository = projectRepository;
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public List<Project> getAll() {
        return getAllProjects();
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        User user = userService.findSelfUser();
        List<Project> projects;

        if (Objects.equals(user.getRole(), Type.ROLE_ADMIN)) {
            projects = projectRepository.findAll();
        } else {
            projects = projectRepository.findAllByUserEqualsOrStudentsContains(user, user);
        }

        for (Project project : projects) {
            this.cleanUserInformations(project.getUser());
            if (ObjectUtils.isNotEmpty(project.getStudents())) {
                project.setStudents(
                        project.getStudents()
                                .stream()
                                .filter(it -> it.getId().equals(user.getId()))
                                .toList()
                );
                for (User student : project.getStudents()) {
                    this.cleanUserInformations(student);
                }
            }
        }

        return projects;
    }

    private void cleanUserInformations(User user) {
        entityManager.detach(user);
        user.setType(null);
        user.setPassword(null);
        user.setStatus(null);
        user.setEmailVerified(null);
        user.setRaSiape(null);
        user.setCpfCnpj(null);
        user.setPartner(null);
        user.setPermissions(null);
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
    }

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

}
