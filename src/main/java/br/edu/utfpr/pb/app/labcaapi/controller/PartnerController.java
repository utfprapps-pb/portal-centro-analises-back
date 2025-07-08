package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.model.Partner;
import br.edu.utfpr.pb.app.labcaapi.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parceiros")
public class PartnerController extends GenericController<Partner, Long> {

    @Autowired
    public PartnerController(PartnerService partnerService) {
        super(partnerService);
    }

}
