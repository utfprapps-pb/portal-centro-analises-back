package com.portal.centro.API.controller;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.service.SolicitationHistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("audit")
public class AuditController extends GenericController<SolicitationHistoric, Long> {

    private final SolicitationHistoricService solicitationHistoricService;
    @Autowired
    public AuditController(SolicitationHistoricService solicitationHistoricService) {
        super(solicitationHistoricService);
        this.solicitationHistoricService = solicitationHistoricService;
    }

    @GetMapping("historyByIdAndNotStatus/{id}/{newStatus}")
    public ResponseEntity historyByIdAndNotStatus(@PathVariable("id") Long id, @PathVariable("newStatus")SolicitationStatus newStatus) {
        return ResponseEntity.ok(this.solicitationHistoricService.findHistoryById(id, newStatus));
    }

    @Override
    @GetMapping("page")
    public ResponseEntity<Page<SolicitationHistoric>> search(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order",required = false) String order,
            @RequestParam(value = "asc",required = false) Boolean asc
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return ResponseEntity.ok(solicitationHistoricService.page(pageRequest));
    }

}
