package com.portal.centro.API.controller;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.Equipment;
import com.portal.centro.API.model.User;
import com.portal.centro.API.service.EquipmentService;
import com.portal.centro.API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    //e faz com que já não seja mais possivel excluir
    //equipamentos da lista
    @Override
    public ResponseEntity deleteById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(equipmentService.editEquipmentStatusToInactive(id));
    }

    //lista os equipamentos ativos
    @Override
    public ResponseEntity getAll() throws Exception {
        return ResponseEntity.ok(equipmentService.findAllEquipmentsActivatedOrInactivated(StatusInactiveActive.ACTIVE));
    }

    //lista os equipamentos inativos
    @GetMapping(path = "findInactive")
    public ResponseEntity findAllInactive() throws Exception {
        return ResponseEntity.ok(equipmentService.findAllEquipmentsActivatedOrInactivated(StatusInactiveActive.INACTIVE));
    }

    //torna um equipamento inativo em ativo novamente
    @PutMapping("activatedEquipment/{id}")
    public ResponseEntity activeEquipmentById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(equipmentService.editEquipmentStatusToActive(id));
    }

    //para que não seja permitido ao usuário editar um equipamento inativo
    @Override
    public ResponseEntity update(@RequestBody @Valid Equipment equipment) throws Exception {
        if (equipment.getStatus().equals(StatusInactiveActive.ACTIVE)){
            return ResponseEntity.ok(equipmentService.save(equipment));
        } else {
            return new ResponseEntity<>("Não é possível editar um equipamento inativo.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //VER A EDIÇÃO DE EQUIPAMENTOS PQ PARECE QUE NÃO ESTÀ FUNCIONANDO
}
