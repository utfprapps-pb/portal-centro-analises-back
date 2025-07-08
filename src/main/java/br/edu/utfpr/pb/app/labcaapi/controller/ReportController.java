package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService reportService) {
        this.service = reportService;
    }

    @GetMapping(value = "/gerar/{id}", produces = "application/octet-stream")
    public ResponseEntity generateReport(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(service.generateReport(id));
    }

//    @GetMapping("/financeiros")
//    public ResponseEntity findAllFinanceiros() {
//        return ResponseEntity.ok(service.getReportsByMenu(ReportContants.FINANCEIROS));
//    }

}
