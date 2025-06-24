package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.model.ProjectStudent;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.ProjectRepository;
import com.portal.centro.API.repository.ProjectStudentRepository;
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
    private final ProjectStudentRepository projectStudentRepository;
    private final AuthService authService;

    @PersistenceContext
    private EntityManager entityManager;


    public ProjectService(UserService userService, ProjectRepository projectRepository, ProjectStudentRepository projectStudentRepository, AuthService authService) {
        super(projectRepository);

        this.projectRepository = projectRepository;
        this.projectStudentRepository = projectStudentRepository;
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
            projects = projectRepository.findAllByUserOrStudent(user);
        }

        for (Project project : projects) {
            this.cleanUserInformations(project.getUser());
            if (ObjectUtils.isNotEmpty(project.getStudents())) {
                for (ProjectStudent student : project.getStudents()) {
                    this.cleanUserInformations(student.getUser());
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
    @Transactional
    public Project update(Project requestBody) throws Exception {
        projectStudentRepository.findAllByProject(requestBody)
                .forEach(projectStudent -> {
                    if (!requestBody.getStudents().contains(projectStudent)) {
                        projectStudentRepository.delete(projectStudent);
                    }
                });
        return super.update(requestBody);
    }

    @Override
    @Transactional
    public Project save(Project project) throws Exception {
        User user = authService.findLoggedUser();
        if (user.getRole().equals(Type.ROLE_PROFESSOR)) {
            project.setUser(user);
        }
        try {
            projectRepository.saveAndFlush(project);
//            if (ObjectUtils.isNotEmpty(project.getStudents())) {
//                for (ProjectStudent student : project.getStudents()) {
//                    student.setProject(project);
//                }
//            }
//            projectRepository.saveAndFlush(project);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return project;
    }

}
