package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.model.Solicitation;
import br.edu.utfpr.pb.app.labcaapi.service.SolicitationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitar")
public class SolicitationController extends GenericController<Solicitation, Long> {

    public SolicitationController(SolicitationService solicitationService) {
        super(solicitationService);
    }

}
