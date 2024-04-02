package com.portal.centro.API.controller;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.Equipment;
import com.portal.centro.API.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("equipments")
public class EquipmentController extends GenericController<Equipment, Long> {
    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        super(equipmentService);
        this.equipmentService = equipmentService;
    }

    //chama a função que deixa o equipamento inativo
    //e faz com que já não seja mais possível excluir
    //equipamentos da lista
    @Override
    public ResponseEntity<Equipment> deleteById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(equipmentService.editEquipmentStatusToInactive(id));
    }

    //lista os equipamentos ativos
    @Override
    public ResponseEntity<List<Equipment>> getAll() throws Exception {
        return ResponseEntity.ok(equipmentService.findAllEquipmentsActivatedOrInactivated(StatusInactiveActive.ACTIVE));
    }

    //lista os equipamentos inativos
    @GetMapping(path = "findInactive")
    public ResponseEntity<List<Equipment>> findAllInactive() throws Exception {
        return ResponseEntity.ok(equipmentService.findAllEquipmentsActivatedOrInactivated(StatusInactiveActive.INACTIVE));
    }

    //torna um equipamento inativo em ativo novamente
    @PutMapping("activatedEquipment/{id}")
    public ResponseEntity<Equipment> activeEquipmentById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(equipmentService.editEquipmentStatusToActive(id));
    }

    //para que não seja permitido ao usuário editar um equipamento inativo
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Equipment equipment) throws Exception {
        if (equipment.getStatus().equals(StatusInactiveActive.ACTIVE)){
            return ResponseEntity.ok(equipmentService.save(equipment));
        } else {
            return new ResponseEntity<>("Não é possível editar um equipamento inativo.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("pagestatus")
    public Page<Equipment> pageStatus(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order",required = false) String order,
            @RequestParam(value = "asc",required = false) Boolean asc,
            @RequestParam(value = "active",required = false) Boolean active
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return equipmentService.findEquipmentByStatusPaged(active ? StatusInactiveActive.ACTIVE : StatusInactiveActive.INACTIVE, pageRequest);
    }

    //VER A EDIÇÃO DE EQUIPAMENTOS PQ PARECE QUE NÃO ESTÀ FUNCIONANDO
}
