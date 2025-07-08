package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.model.Finance;
import br.edu.utfpr.pb.app.labcaapi.service.FinanceService;
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
