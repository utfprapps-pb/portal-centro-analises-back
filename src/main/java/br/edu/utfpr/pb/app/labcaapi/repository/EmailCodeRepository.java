package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.EmailCode;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCodeRepository extends GenericRepository<EmailCode,Long> {

    EmailCode findEmailCodeByCode(String code);

}
