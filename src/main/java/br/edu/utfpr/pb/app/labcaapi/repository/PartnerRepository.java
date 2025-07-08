package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Partner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends GenericRepository<Partner, Long> {

    @Query(value = "select tdr.domain from DomainRole tdr where tdr.role = 2")
    List<String> findAllEstudantes();

}
