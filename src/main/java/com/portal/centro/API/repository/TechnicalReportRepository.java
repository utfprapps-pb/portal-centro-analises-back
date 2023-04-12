package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.TechnicalReport;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalReportRepository extends GenericRepository<TechnicalReport, Long> {
}
