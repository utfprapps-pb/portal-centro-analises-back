package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.Equipment;
import br.edu.utfpr.pb.app.labcaapi.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService extends GenericService<Equipment, Long> {

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        super(equipmentRepository);
    }

}

