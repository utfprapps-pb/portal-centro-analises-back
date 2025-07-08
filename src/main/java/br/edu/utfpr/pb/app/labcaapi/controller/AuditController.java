package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.model.SolicitationHistoric;
import br.edu.utfpr.pb.app.labcaapi.service.SolicitationHistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("audit")
public class AuditController extends GenericController<SolicitationHistoric, Long> {

    @Autowired
    public AuditController(SolicitationHistoricService solicitationHistoricService) {
        super(solicitationHistoricService);
    }

}
