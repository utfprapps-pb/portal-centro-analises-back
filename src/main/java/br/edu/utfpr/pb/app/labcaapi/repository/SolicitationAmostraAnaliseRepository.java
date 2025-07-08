package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Equipment;
import br.edu.utfpr.pb.app.labcaapi.model.SolicitationAmostraAnalise;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationAmostraAnaliseRepository extends GenericRepository<SolicitationAmostraAnalise, Long> {

    List<SolicitationAmostraAnalise> findAllByEquipment(Equipment equipment);

}
