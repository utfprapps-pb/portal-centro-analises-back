package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.minio.service.impl.MinioServiceImpl;
import com.portal.centro.API.model.Attachment;
import com.portal.centro.API.model.ObjectReturn;
import com.portal.centro.API.repository.AttachmentRepository;
import io.minio.StatObjectResponse;
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
