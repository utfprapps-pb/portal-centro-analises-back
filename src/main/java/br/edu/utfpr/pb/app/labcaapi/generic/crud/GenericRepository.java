package br.edu.utfpr.pb.app.labcaapi.generic.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    
}
