package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Project;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends GenericRepository<Project, Long> {

    @Query("SELECT DISTINCT p FROM Project p" +
            "            LEFT JOIN ProjectStudent tps on tps.project = p" +
            "            WHERE p.user = :user OR tps.user = :user")
    List<Project> findAllByUserOrStudent(User user);

}
