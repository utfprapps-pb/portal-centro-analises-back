package com.portal.centro.API.controller;

import com.portal.centro.API.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    @GetMapping(path = "/usuariotipo")
    public ResponseEntity getGraficoUsuarioTipo() throws Exception {
        return ResponseEntity.ok(dashboardService.getGraficoUsuarioRole());
    }

    @GetMapping(path = "/usuariosituacao")
    public ResponseEntity getGraficoUsuarioSituacao() throws Exception {
        return ResponseEntity.ok(dashboardService.getGraficoUsuarioSituacao());
    }

    @GetMapping(path = "/equipamentosituacao")
    public ResponseEntity getGraficoEquipamentoSituacao() throws Exception {
        return ResponseEntity.ok(dashboardService.getGraficoEquipamentoSituacao());
    }
}
