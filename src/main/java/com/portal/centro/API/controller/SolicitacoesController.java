package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.service.SolicitationHistoricService;
import com.portal.centro.API.service.SolicitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacoesController extends GenericController<Solicitation, Long> {

    private final SolicitationService solicitationService;
    private final SolicitationHistoricService solicitationHistoricService;

    public SolicitacoesController(GenericService<Solicitation, Long> genericService,
                                  SolicitationService solicitationService,
                                  SolicitationHistoricService solicitationHistoricService) {
        super(genericService);
        this.solicitationService = solicitationService;
        this.solicitationHistoricService = solicitationHistoricService;
    }

    @GetMapping("/buscar-historicos")
    public ResponseEntity<List<SolicitationHistoric>> getHistorico() {
        return ResponseEntity.ok(solicitationHistoricService.getAll());
    }

}
