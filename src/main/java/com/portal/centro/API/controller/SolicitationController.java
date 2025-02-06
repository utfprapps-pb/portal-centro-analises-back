package com.portal.centro.API.controller;

import com.portal.centro.API.dto.SolicitationRequestDto;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.service.SolicitationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitar")
public class SolicitationController extends GenericController<Solicitation, Long> {

    private final SolicitationService solicitationService;

    public SolicitationController(GenericService<Solicitation, Long> genericService,
                                  SolicitationService solicitationService) {
        super(genericService);
        this.solicitationService = solicitationService;
    }

    /**
     * Atualiza a situação da solicitação
     */
    @PostMapping("/status")
    public ResponseEntity<Solicitation> alterStatus(@RequestBody @Valid SolicitationRequestDto solicitationRequestDto) throws Exception {
        return ResponseEntity.ok(this.solicitationService.updateStatus(solicitationRequestDto));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Solicitation>> getPending() {
        return ResponseEntity.ok(solicitationService.getPending());
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Solicitation> aproveProfessorSolicitation(@PathVariable Long id) {
        return ResponseEntity.ok(solicitationService.approveProfessor(id));
    }

    @PostMapping("/approve-lab/{id}")
    public ResponseEntity<Solicitation> aproveLabSolicitation(@PathVariable Long id) {
        return ResponseEntity.ok(solicitationService.approveLab(id));
    }

}
