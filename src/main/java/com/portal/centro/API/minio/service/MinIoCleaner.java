package com.portal.centro.API.minio.service;

import com.portal.centro.API.minio.service.impl.MinioServiceImpl;
import com.portal.centro.API.model.Attachment;
import com.portal.centro.API.repository.SolicitationAttachmentsRepository;
import com.portal.centro.API.service.AttachmentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MinIoCleaner {

    private final MinioServiceImpl minioService;

    private final SolicitationAttachmentsRepository solicitationAttachmentsRepository;
    private final AttachmentService attachmentService;

    public MinIoCleaner(MinioServiceImpl minioService, AttachmentService attachmentService, SolicitationAttachmentsRepository solicitationAttachmentsRepository) {
        this.minioService = minioService;
        this.attachmentService = attachmentService;
        this.solicitationAttachmentsRepository = solicitationAttachmentsRepository;
    }

    // O cron "0 0 0 * * ?" indica que a tarefa será executada todos os dias à meia-noite.
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 24 22 * * ?")
    public void limparAnexos() {
        List<Attachment> attachmentsBanco = attachmentService.getAll();

        // Obter a lista de buckets únicos registrados no banco de dados
        List<String> bucketsDB = attachmentsBanco.stream().map(Attachment::getBucket).toList();
        // Obter a lista de buckets disponíveis no serviço Minio
        List<String> bucketMinio = minioService.listBucketName();

        // Removi os buckets do minio que não estão no banco
        List<String> bucketMinioSemBanco = bucketMinio.stream().filter(bucket -> !bucketsDB.contains(bucket)).toList();
        for (String bucket : bucketMinioSemBanco) {
            minioService.removeListObject(bucket, minioService.listObjectNames(bucket));
            minioService.removeBucket(bucket);
        }

        bucketMinio = minioService.listBucketName();
        for (String bucket : bucketMinio) {
            List<String> anexosMinio = minioService.listObjectNames(bucket);
            List<String> anexosBanco = attachmentsBanco
                    .stream()
                    .filter(it -> it.getBucket().equals(bucket))
                    .map(Attachment::getFileHash)
                    .toList();

            List<String> anexosSemBanco = anexosMinio
                    .stream()
                    .filter(it -> !anexosBanco.contains(it))
                    .toList();

            minioService.removeListObject(bucket, anexosSemBanco);
        }

        bucketMinio = minioService.listBucketName();
        for (String bucket : bucketMinio) {
            List<String> anexosMinio = minioService.listObjectNames(bucket);
            if (ObjectUtils.isNotEmpty(anexosMinio)) {
                minioService.removeBucket(bucket);
            }
        }

    }
}
