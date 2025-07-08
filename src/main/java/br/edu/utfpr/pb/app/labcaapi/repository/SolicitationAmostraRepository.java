package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.SolicitationAmostra;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationAmostraRepository extends GenericRepository<SolicitationAmostra, Long> {

    List<SolicitationAmostra> findAllByFormId(Long formId);

}
