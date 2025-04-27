package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.Finance;
import com.portal.centro.API.service.FinanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financeiro")
public class FinanceController extends GenericController<Finance, Long> {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        super(financeService);
        this.financeService = financeService;
    }

}
