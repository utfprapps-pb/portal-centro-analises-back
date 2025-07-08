package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.model.DomainRole;
import br.edu.utfpr.pb.app.labcaapi.service.DomainRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dominios")
public class DomainRoleController extends GenericController<DomainRole, Long> {

    public DomainRoleController(DomainRoleService domainRoleService) {
        super(domainRoleService);
    }

}
