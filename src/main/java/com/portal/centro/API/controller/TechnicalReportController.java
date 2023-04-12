package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.TechnicalReport;
import com.portal.centro.API.service.TechnicalReportService;
import org.springframework.beans.factory.annotation.Autowired;

public class TechnicalReportController extends GenericController<TechnicalReport, Long> {

    @Autowired
    public TechnicalReportController(TechnicalReportService technicalReportService) {
        super(technicalReportService);
    }

}
