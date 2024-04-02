package com.portal.centro.API.repository;

import jakarta.persistence.Tuple;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationRepository extends GenericRepository<Solicitation, Long> {

    List<Solicitation> findAllByProject_UserAndStatus(User user, SolicitationStatus status);
    Page<Solicitation> findAllByProject_UserAndStatus(User user, SolicitationStatus status, Pageable pageable);

    List<Solicitation> findAllByStatus(SolicitationStatus status);
    Page<Solicitation> findAllByStatus(SolicitationStatus status, Pageable pageable);
    @Query(
            value = "select " +
                    "ROW_NUMBER() OVER (ORDER BY status) AS id, " +
                    "case  " +
                    " when s.status = 0 then 'Ag. Conf. do Orientador' " +
                    " when s.status = 1 then 'Ag. Conf. do Laboratório' " +
                    " when s.status = 2 then 'Ag. amostra' " +
                    " when s.status = 3 then 'Ag. Análise' " +
                    " when s.status = 4 then 'Ag. Pagamento' " +
                    " when s.status = 5 then 'Recusado' " +
                    " when s.status = 6 then 'Concluído' " +
                    " else null " +
                    "end as label " +
                    ", count(s.status) as value " +
                    "from solicitation s  " +
                    "group by s.status " +
                    "order by s.status",
            nativeQuery = true)
    List<Tuple> findGraficoSolicitacoesNative();

    @Query(
            value = "select " +
                    "ROW_NUMBER() OVER (ORDER BY equipment_id) AS id, " +
                    "e.short_name as label, count(s.equipment_id) as value " +
                    "from solicitation s " +
                    "join equipment e on e.id = s.equipment_id " +
                    "group by e.short_name, s.equipment_id  " +
                    "order by count(s.equipment_id) desc " +
                    "limit 5",
            nativeQuery = true)
    List<Tuple> findGraficoEquipamentoSolicitacaoNative();

}
