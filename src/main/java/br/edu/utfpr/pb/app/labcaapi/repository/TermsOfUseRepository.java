package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.TermsOfUse;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsOfUseRepository extends GenericRepository<TermsOfUse, Long> {

}
