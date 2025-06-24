package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Equipment;
import com.portal.centro.API.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService extends GenericService<Equipment, Long> {

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        super(equipmentRepository);
    }

}

