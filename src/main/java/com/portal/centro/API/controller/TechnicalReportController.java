package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.TechnicalReport;
import com.portal.centro.API.service.TechnicalReportService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("report")
public class TechnicalReportController extends GenericController<TechnicalReport, Long> {

    @Autowired
    public TechnicalReportController(TechnicalReportService technicalReportService, ModelMapper modelMapper) {
        super(technicalReportService);
        this.technicalReportService = technicalReportService;
        this.modelMapper = modelMapper;
    }

    private  final  TechnicalReportService technicalReportService;
    private  final ModelMapper modelMapper;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public TechnicalReport saveTechnicalReport(@RequestPart("technical_report") @Valid TechnicalReport entity, @RequestPart("image") @Valid MultipartFile file) throws Exception {
        return technicalReportService.save(entity, file);
    }
    //A requisição HTTP GET irá vir com o código do produto e irá retornar a imagem no corpo da resposta.
    @GetMapping(value = "download/{id}")
    public  void downloadFile(@PathVariable("id") Long id, HttpServletResponse response) {
        TechnicalReport technicalReport = technicalReportService.findBySolicitationId(id);
        if(technicalReport != null)
        technicalReportService.downloadFile(technicalReport, response);
    }

    @PostMapping(path = "text")
    public ResponseEntity<?> saveTechnicalrReportText(@RequestBody TechnicalReport report) {
        try {
            technicalReportService.generateReport(report);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "vinculado/{id}")
    public Boolean vinculadoASolicitacao(@PathVariable Long id){
        return technicalReportService.findBySolicitationId(id) == null;
    }

}
