package com.portal.centro.API.repository;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Equipment;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends GenericRepository<Equipment,Long> {
    List<Equipment> findAllByStatus(StatusInactiveActive status);
    Page<Equipment> findAllByStatus(StatusInactiveActive status, PageRequest pageRequest);

    @Query(
            value = "select " +
                    "ROW_NUMBER() OVER (ORDER BY status) AS id, " +
            "case  " +
            "   when e.status = 0 then 'Inativo' " +
            "   when e.status = 1 then 'Ativo' " +
            "   else null " +
            "end as label, " +
            "count(e.id) as value " +
            "from equipment e " +
            "group by e.status",
    nativeQuery = true)
    List<Tuple> findGraficoEquipamentoSituacaoNative();
}
