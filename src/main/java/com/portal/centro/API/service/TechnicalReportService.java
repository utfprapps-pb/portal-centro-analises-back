package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.TechnicalReport;
import com.portal.centro.API.repository.TechnicalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechnicalReportService extends GenericService<TechnicalReport, Long> {

    @Autowired
    public TechnicalReportService(TechnicalReportRepository technicalReportRepository) {
        super(technicalReportRepository);
    }

}
