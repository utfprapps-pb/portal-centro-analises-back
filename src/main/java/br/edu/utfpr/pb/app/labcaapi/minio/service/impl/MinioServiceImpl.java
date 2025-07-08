package br.edu.utfpr.pb.app.labcaapi.minio.service.impl;

import br.edu.utfpr.pb.app.labcaapi.minio.config.MinioConfig;
import br.edu.utfpr.pb.app.labcaapi.minio.service.MinioService;
import br.edu.utfpr.pb.app.labcaapi.minio.util.MinioUtil;
import br.edu.utfpr.pb.app.labcaapi.model.Attachment;
import io.minio.ObjectWriteResponse;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {
    private final MinioUtil minioUtil;
    private final MinioConfig minioProperties;

    public MinioServiceImpl(MinioUtil minioUtil, MinioConfig minioProperties) {
        this.minioUtil = minioUtil;
        this.minioProperties = minioProperties;
    }

    @SneakyThrows
    @Override
    public Attachment putObject(MultipartFile multipartFile, String bucketName) {
        try {
            bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioProperties.getBucketName();
            if (!this.bucketExists(bucketName)) {
                if (!this.makeBucket(bucketName)) {
                    log.error("Falha ao criar bucket.");
                    throw new Exception("Falha ao criar bucket.");
                }
            }
            String fileName = multipartFile.getOriginalFilename();
            Long fileSize = multipartFile.getSize();
            String fileHash = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
            ObjectWriteResponse objectWriteResponse = minioUtil.putObject(bucketName, multipartFile, fileHash, multipartFile.getContentType());
            return Attachment.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .fileHash(fileHash)
                    .contentType(multipartFile.getContentType())
                    .bucket(objectWriteResponse.bucket())
                    .fileSize(fileSize)
                    .url(getObjectUrl(bucketName, fileHash))
                    .createdAt(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.error("MinioServiceImpl -> putObject: " + e.getMessage());
            return null;
        }
    }

    @Override
    public InputStream downloadObject(String bucketName, String objectName) {
        return minioUtil.getObject(bucketName, objectName);
    }

    @Override
    public boolean bucketExists(String bucketName) {
        return minioUtil.bucketExists(bucketName);
    }

    @Override
    public boolean makeBucket(String bucketName) {
        return minioUtil.makeBucket(bucketName);
    }

    @Override
    public List<String> listBucketName() {
        return minioUtil.listBucketNames();
    }

    @Override
    public List<Bucket> listBuckets() {
        return minioUtil.listBuckets();
    }

    @Override
    public boolean removeBucket(String bucketName) {
        return minioUtil.removeBucket(bucketName);
    }

    @Override
    public List<String> listObjectNames(String bucketName) {
        return minioUtil.listObjectNames(bucketName);
    }

    @Override
    public boolean removeObject(String bucketName, String objectName) {
        return minioUtil.removeObject(bucketName, objectName);
    }

    @Override
    public boolean removeListObject(String bucketName, List<String> objectNameList) {
        return minioUtil.removeObject(bucketName, objectNameList);
    }

    @Override
    public String getObjectUrl(String bucketName, String objectName) {
        return minioUtil.getObjectUrl(bucketName, objectName);
    }
}
