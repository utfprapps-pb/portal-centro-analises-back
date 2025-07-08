package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.TermsOfUse;
import br.edu.utfpr.pb.app.labcaapi.service.TermsOfUseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/termos-de-uso")
public class TermsOfUseController extends GenericController<TermsOfUse, Long> {

    private final TermsOfUseService termsOfUseService;

    public TermsOfUseController(GenericService<TermsOfUse, Long> genericService,
                                TermsOfUseService termsOfUseService) {
        super(genericService);
        this.termsOfUseService = termsOfUseService;
    }

}
