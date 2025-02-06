package com.portal.centro.API.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("report")
public class TechnicalReportController {} // extends GenericController<TechnicalReport, Long> {
//
//    @Autowired
//    public TechnicalReportController(TechnicalReportService technicalReportService, ModelMapper modelMapper) {
//        super(technicalReportService);
//        this.technicalReportService = technicalReportService;
//        this.modelMapper = modelMapper;
//    }
//
//    private  final  TechnicalReportService technicalReportService;
//    private  final ModelMapper modelMapper;
//
//    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
//    public  TechnicalReport saveTechnicalReport(@RequestPart("technical_report") @Valid TechnicalReport entity, @RequestPart("image") @Valid List<MultipartFile> file) throws Exception {
//        return technicalReportService.save(entity, file);
//    }
//    //A requisição HTTP GET irá vir com o código do produto e irá retornar a imagem no corpo da resposta.
//    @GetMapping(value = "download/{id}")
//    public  void downloadFile(@PathVariable("id") Long id, HttpServletResponse response) {
//        technicalReportService.downloadFile(id, response);
//    }
//
//    @PostMapping(path = "text")
//    public ResponseEntity<?> saveTechnicalrReportText(@RequestBody TechnicalReport report) {
//        try {
//            technicalReportService.generateReport(report);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//}
