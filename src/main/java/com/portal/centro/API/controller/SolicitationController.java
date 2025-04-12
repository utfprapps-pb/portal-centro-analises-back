package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.service.SolicitationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitar")
public class SolicitationController extends GenericController<Solicitation, Long> {

    public SolicitationController(SolicitationService solicitationService) {
        super(solicitationService);
    }

}
