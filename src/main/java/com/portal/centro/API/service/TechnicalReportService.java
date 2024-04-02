package com.portal.centro.API.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
//import javax.servlet.http.HttpServletResponse;


@Service
@Slf4j
public class TechnicalReportService {}
//extends GenericService<TechnicalReport, Long> {
//
//
//    @Autowired
//    public TechnicalReportService(TechnicalReportRepository technicalReportRepository, MinioService minioService, TemplateEngine templateEngine) {
//        super(technicalReportRepository);
//        this.technicalReportRepository = technicalReportRepository;
//        this.minioService = minioService;
//        this.templateEngine = templateEngine;
//    }
//
//    private  final  TechnicalReportRepository technicalReportRepository;
//    private  final MinioService minioService;
//    private TemplateEngine templateEngine;
//
//
//    protected JpaRepository<TechnicalReport, Long> getRepository() {
//        return  this.technicalReportRepository;
//    }
//
//    public TechnicalReport save(TechnicalReport entity, List<MultipartFile> file) throws Exception {
//
//        List<SolicitationAttachments> fileLists = new ArrayList<>();
//
//        file.forEach((multipartFile) -> {
//            String fileType = FileTypeUtils.getFileType(multipartFile);
//            FileResponse fileResponse = minioService.putObject(multipartFile, "central-de-analises", fileType);
//            SolicitationAttachments fileList = new SolicitationAttachments();
//            fileList.setFileName(fileResponse.getFilename());
//            fileList.setContentType(fileResponse.getContentType());
//            fileLists.add(fileList);
//        });
//
//        entity.setSolicitationAttachments(fileLists);
//        return super.save(entity);
//    }
//
//    public  void downloadFile(Long id, HttpServletResponse response) {
//        InputStream in = null;
//        try {
//            // TODO
//            TechnicalReport technicalReport = this.findOneById(id);
//
//            for (SolicitationAttachments solicitationAttachments : technicalReport.getSolicitationAttachments()) {
//
//                in = minioService.downloadObject("central-de-analises", solicitationAttachments.getFileName());
//                try {
//                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(solicitationAttachments.getFileName(), "UTF-8"));
//                    response.setCharacterEncoding("UTF-8");
//                    // Remove bytes from InputStream Copied to the OutputStream.
//                    IOUtils.copy(in, response.getOutputStream());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    log.error(e.getMessage());
//                }
//            }
//        }
//    }
//
//    public void generateReport(TechnicalReport report) throws Exception {
//        Context context = new Context();
//        context.setVariables(DataToMap(report));
//
//        String html = templateEngine.process("report", context);
//
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(html);
//        renderer.layout();
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        renderer.createPDF(out);
//
//        FileResponse fileResponse = minioService.putObject((MultipartFile) new ByteArrayInputStream(out.toByteArray()), "central-de-analises",  "PDF");
//
//        super.save(report);
//    }
//
//    public Map<String, Object> DataToMap(TechnicalReport report) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("num", report.getId());
//        map.put("reportDetails", report.getSolicitation());
//        return map;
//    }
//
//    public TechnicalReport findBySolicitationId(Long id){
//        return technicalReportRepository.findBySolicitationId(id);
//    }
//
//}
