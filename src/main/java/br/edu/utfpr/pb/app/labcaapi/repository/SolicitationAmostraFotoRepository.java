package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.SolicitationAmostraFoto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationAmostraFotoRepository extends GenericRepository<SolicitationAmostraFoto, Long> {

    List<SolicitationAmostraFoto> findAllByAmostraId(Long amostraId);

}
