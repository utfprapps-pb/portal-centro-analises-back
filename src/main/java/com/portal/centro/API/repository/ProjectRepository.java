package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends GenericRepository<Project, Long> {

    List<Project> findAllByStudentsContains(User user);

    List<Project> findAllByTeacher(User teacher);

    Page<Project> findAllByTeacher(User teacher, PageRequest pageRequest);

}
