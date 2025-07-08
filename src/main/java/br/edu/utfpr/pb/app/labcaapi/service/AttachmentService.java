package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.minio.service.impl.MinioServiceImpl;
import br.edu.utfpr.pb.app.labcaapi.model.Attachment;
import br.edu.utfpr.pb.app.labcaapi.model.ObjectReturn;
import br.edu.utfpr.pb.app.labcaapi.repository.AttachmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService extends GenericService<Attachment, Long> {

    private final MinioServiceImpl minioService;

    public AttachmentService(AttachmentRepository attachmentRepository, MinioServiceImpl minioService) {
        super(attachmentRepository);
        this.minioService = minioService;
    }

    @Override
    public ObjectReturn deleteById(Long id) throws Exception {
        Attachment attachment = findOneById(id);
        ObjectReturn result = super.deleteById(id);
        this.minioService.removeObject(attachment.getBucket(), attachment.getFileHash());
        return result;
    }
}
