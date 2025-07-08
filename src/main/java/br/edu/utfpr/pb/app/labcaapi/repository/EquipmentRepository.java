package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Equipment;
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
            "from tb_equipment e " +
            "group by e.status",
    nativeQuery = true)
    List<Tuple> findGraficoEquipamentoSituacaoNative();
}
