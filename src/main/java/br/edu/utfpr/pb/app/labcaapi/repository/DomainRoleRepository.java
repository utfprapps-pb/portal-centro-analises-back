package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.DomainRole;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRoleRepository extends GenericRepository<DomainRole, Long> {

    Optional<DomainRole> findByDomain(String domain);

}
