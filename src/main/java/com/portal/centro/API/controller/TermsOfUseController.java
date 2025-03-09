package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.TermsOfUse;
import com.portal.centro.API.service.TermsOfUseService;
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
