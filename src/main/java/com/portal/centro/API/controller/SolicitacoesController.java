package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.service.SolicitationHistoricService;
import com.portal.centro.API.service.SolicitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacoesController extends GenericController<Solicitation, Long> {

    private final SolicitationService solicitationService;
    private final SolicitationHistoricService solicitationHistoricService;

    public SolicitacoesController(SolicitationService solicitationService,
                                  SolicitationHistoricService solicitationHistoricService) {
        super(solicitationService);
        this.solicitationService = solicitationService;
        this.solicitationHistoricService = solicitationHistoricService;
    }

    @GetMapping("/buscar-historicos")
    public ResponseEntity<List<SolicitationHistoric>> getHistorico() {
        return ResponseEntity.ok(solicitationHistoricService.getAll());
    }

    @GetMapping("/buscar-historicos/{id}")
    public ResponseEntity<List<SolicitationHistoric>> getHistoricoById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(solicitationHistoricService.findHistoryBySolicitationId(id));
    }

    @PutMapping("/atualizar-status")
    public ResponseEntity atualizarStatus(@RequestBody SolicitationHistoric historico) throws Exception {
        solicitationHistoricService.verificaStatusValido(historico);
        solicitationHistoricService.save(historico);
        return ResponseEntity.ok(solicitationService.findOneById(historico.getSolicitation().getId()));
    }

}
