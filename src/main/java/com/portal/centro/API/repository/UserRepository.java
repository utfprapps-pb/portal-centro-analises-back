package com.portal.centro.API.repository;

import jakarta.persistence.Tuple;
import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.User;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllByRole(Type role);

    List<User> findAllByEmailContainingIgnoreCase(String domain);


    @Query(
            value = "select " +
                    "ROW_NUMBER() OVER (ORDER BY role) AS id, " +
                    "case  " +
                    "   when u.role = 0 then 'Professor' " +
                    "   when u.role = 1 then 'Estudante' " +
                    "   when u.role = 2 then 'Externo' " +
                    "   when u.role = 3 then 'Admin' " +
                    "   when u.role = 4 then 'Parceiro' " +
                    "   else null " +
                    "end as label, " +
                    "count(u.role) as value " +
                    "from tb_user u  " +
                    "group by u.role " +
                    "order by u.role",
            nativeQuery = true)
    List<Tuple> findGraficoUsuarioTipoNative();

    @Query(
            value = "select " +
                    "ROW_NUMBER() OVER (ORDER BY status) AS id, " +
                    "case  " +
                    "   when u.status = 0 then 'Inativo' " +
                    "   when u.status = 1 then 'Ativo' " +
                    "   else null " +
                    "end as label, " +
                    "count(u.id) as value " +
                    "from tb_user u " +
                    "group by u.status",
            nativeQuery = true)
    List<Tuple> findGraficoUsuarioSituacaoNative();

}
