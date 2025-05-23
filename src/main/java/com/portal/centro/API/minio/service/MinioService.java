package com.portal.centro.API.minio.service;

import com.portal.centro.API.model.Attachment;
import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

//As classes **MinioService** e **MinioServiceImp** contém a assinatura e implementação dos principais métodos utilizados para acesso aos serviços disponibilizados pelo **Minio**.

public interface MinioService {
    // Upload files in the bucket
    Attachment putObject(MultipartFile multipartFile, String bucketName);

    // Download file from bucket
    InputStream downloadObject(String bucketName, String objectName);

    //Check whether bucket already exists
    boolean bucketExists(String bucketName);

    // Create a bucket
    boolean makeBucket(String bucketName);

    // List all bucket names
    List<String> listBucketName();

    //List all buckets
    List<Bucket> listBuckets();

    // Delete Bucket by Name
    boolean removeBucket(String bucketName);

    // List all object names in the bucket
    List<String> listObjectNames(String bucketName);

    // Delete file in bucket
    boolean removeObject(String bucketName, String objectName);

    // Delete files in bucket
    boolean removeListObject(String bucketName, List<String> objectNameList);

    // Get file path from bucket
    String getObjectUrl(String bucketName, String objectName);
}
