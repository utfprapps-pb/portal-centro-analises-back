package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.TechnicalReport;
import com.portal.centro.API.service.TechnicalReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

public class TechnicalReportController extends GenericController<TechnicalReport, Long> {

    @Autowired
    public TechnicalReportController(TechnicalReportService technicalReportService, ModelMapper modelMapper) {
        super(technicalReportService);
        this.technicalReportService = technicalReportService;
        this.modelMapper = modelMapper;
    }

    private  final  TechnicalReportService technicalReportService;
    private  final ModelMapper modelMapper;

    /*
		Será realizada uma requisição a partir do client enviando um FormData.
		O corpo da requisição será um JSON com {"technical_report": {...}, "arquivo": ...}
		Que será tratado na classe Service.
	*/
    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public  TechnicalReport saveTechnicalReport(@RequestPart("technical_report") @Valid TechnicalReport entity, @RequestPart("image") @Valid MultipartFile file) throws Exception {
        return technicalReportService.save(entity, file);
    }
    //A requisição HTTP GET irá vir com o código do produto e irá retornar a imagem no corpo da resposta.
    @GetMapping(value = "download/{id}")
    public  void downloadFile(@PathVariable("id") Long id, HttpServletResponse response) {
        technicalReportService.downloadFile(id, response);
    }

}
