package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.DomainRole;
import com.portal.centro.API.service.DomainRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dominios")
public class DomainRoleController extends GenericController<DomainRole, Long> {

    public DomainRoleController(DomainRoleService domainRoleService) {
        super(domainRoleService);
    }

}
