package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.EmailConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigRepository extends GenericRepository<EmailConfig, Long> {
}
