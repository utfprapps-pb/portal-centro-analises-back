package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends GenericRepository<Permission,Long> {

}

