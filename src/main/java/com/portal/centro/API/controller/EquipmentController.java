package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.Equipment;
import com.portal.centro.API.model.User;
import com.portal.centro.API.service.EquipmentService;
import com.portal.centro.API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/equipments")
public class EquipmentController extends GenericController<Equipment, Long> {

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        super(equipmentService);
    }

}
