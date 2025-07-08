package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.SolicitationHistoric;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationHistoricRepository extends GenericRepository<SolicitationHistoric, Long> {

    List<SolicitationHistoric> findAllBySolicitation_CreatedByOrSolicitation_Responsavel(User user, User responsavel);

    List<SolicitationHistoric> findAllBySolicitationId(Long id);

}