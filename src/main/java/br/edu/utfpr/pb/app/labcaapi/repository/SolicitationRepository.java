package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Solicitation;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationRepository extends GenericRepository<Solicitation, Long> {

    List<Solicitation> findAllByCreatedByOrResponsavel(User creator, User responsavel);

}
