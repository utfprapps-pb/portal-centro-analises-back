package com.portal.centro.API.service;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Equipment;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService extends GenericService<Equipment, Long> {
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        super(equipmentRepository);
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment save(Equipment requestBody) throws Exception {
        return super.save(requestBody);
    }

    //modifica o status de um equipamento de ativo para inativo
    public Equipment editEquipmentStatusToInactive(Long id) throws Exception {
        Optional<Equipment> equipment = equipmentRepository.findById(id);

        equipment.get().setStatus(StatusInactiveActive.INACTIVE);

        return super.save(equipment.get());
    }

    //modifica o status de um equipamento de inativo para ativo
    public Equipment editEquipmentStatusToActive(Long id) throws Exception {
        Optional<Equipment> equipment = equipmentRepository.findById(id);

        equipment.get().setStatus(StatusInactiveActive.ACTIVE);

        return super.save(equipment.get());
    }

    public List<Equipment> findAllEquipmentsActivatedOrInactivated(StatusInactiveActive status) {
        return equipmentRepository.findAllByStatus(status);
    }

    public Page<Equipment> findEquipmentByStatusPaged(StatusInactiveActive status, PageRequest pageRequest) {
        return equipmentRepository.findAllByStatus(status, pageRequest);
    }

}

