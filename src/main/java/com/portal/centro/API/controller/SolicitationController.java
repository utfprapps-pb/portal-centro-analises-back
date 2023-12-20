package com.portal.centro.API.controller;

import com.portal.centro.API.dto.SolicitationResponseDto;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.TransactionType;
import com.portal.centro.API.dto.SolicitationResponseDto;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.generic.response.GenericResponse;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.Transaction;
import com.portal.centro.API.model.User;
import com.portal.centro.API.service.SolicitationService;
import com.portal.centro.API.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("solicitation")
public class SolicitationController extends GenericController<Solicitation, Long> {

    private final SolicitationService solicitationService;

    public SolicitationController(
            GenericService<Solicitation, Long> genericService,
            SolicitationService solicitationService) {
        super(genericService);
        this.solicitationService = solicitationService;
    }

    @PostMapping("/status")
    public GenericResponse alterStatus(@RequestBody @Valid SolicitationResponseDto responseDto) throws Exception {
        this.solicitationService.updateStatus(responseDto);
        return new GenericResponse("Status atualizado.");
    }

    @GetMapping("/pending")
    public ResponseEntity getPending() {
        return ResponseEntity.ok(solicitationService.getPending());
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity aproveProfessorSolicitation(@PathVariable Long id) {
        return ResponseEntity.ok(solicitationService.approveProfessor(id));
    }

    @PostMapping("/approvelab/{id}")
    public ResponseEntity aproveLabSolicitation(@PathVariable Long id) {
        return ResponseEntity.ok(solicitationService.approveLab(id));
    }

    @GetMapping("/pendingpage")
    public Page<Solicitation> getPendingPage(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order",required = false) String order,
            @RequestParam(value = "asc",required = false) Boolean asc
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return solicitationService.getPendingPage(pageRequest);
    }

}
