package com.portal.centro.API.service;

import com.portal.centro.API.dto.ProjectDTO;
import com.portal.centro.API.dto.RetrieveProjectInfo;
import com.portal.centro.API.dto.TeacherDTO;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService extends GenericService<Project, Long> {

    private final UserService userService;
    private final ProjectRepository projectRepository;

    public ProjectService(GenericRepository<Project, Long> genericRepository , UserService userService, ProjectRepository projectRepository) {
        super(projectRepository);

        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public ResponseEntity linkUserToProject(Long studentId, Project linkTo) {
        Optional<Project> project = projectRepository.findById(linkTo.getId());

        User student = userService.findOneById(studentId);
        project.get().getStudents().add(student);

        return ResponseEntity.ok(projectRepository.save(project.get()));
    }

    public ResponseEntity getAllProjects()  {
        User user = userService.findSelfUser();
        List<Project> projects = projectRepository.findAllByStudentsContains(user);

        RetrieveProjectInfo info = new RetrieveProjectInfo();
        TeacherDTO teacher = TeacherDTO.builder().build();

        if(!projects.isEmpty()) {
            teacher = TeacherDTO.builder()
                    .name(projects.get(0).getUser().getName())
                    .id(projects.get(0).getUser().getId())
                    .email(projects.get(0).getUser().getEmail())
                    .build();
        }

        List<ProjectDTO> output = projects.stream().map(projectObject -> ProjectDTO.builder()
                .id(projectObject.getId())
                .description(projectObject.getDescription())
                .subject(projectObject.getSubject())
                .build()
        ).collect(Collectors.toList());

        info.setTeacherDTO(teacher);
        info.setProjectDTOS(output);

        return ResponseEntity.ok(info);
    }

    @Override
    public Project save(Project requestBody) throws Exception {
        Project project = new Project();
        try{
            project = projectRepository.save(requestBody);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return project;
    }

    @Override
    public Page<Project> page(PageRequest pageRequest) {
        User user = userService.findSelfUser();
        switch (user.getRole()) {
            case ROLE_ADMIN:
                return super.page(pageRequest);
            case ROLE_PROFESSOR:
                return projectRepository.findAllByUser(user, pageRequest);
            default:
                throw new ValidationException("Você não possui permissão para acessar este recurso.");
        }
    }
}
