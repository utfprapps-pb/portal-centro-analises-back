package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Project;
import br.edu.utfpr.pb.app.labcaapi.model.ProjectStudent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStudentRepository extends GenericRepository<ProjectStudent, Long> {

    List<ProjectStudent> findAllByProject(Project projectId);

}
