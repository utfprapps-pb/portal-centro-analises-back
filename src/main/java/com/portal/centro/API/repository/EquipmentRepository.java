package com.portal.centro.API.repository;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Equipment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends GenericRepository<Equipment,Long> {
    List<Equipment> findAllByStatus(StatusInactiveActive status);
}
