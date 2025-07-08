package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.minio.service.impl.MinioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/minio")
public class MinioController {

    private final MinioServiceImpl minioService;

    @Autowired
    public MinioController(MinioServiceImpl minioService) {
        this.minioService = minioService;
    }

    @PostMapping(path = "/upload")
    public ResponseEntity upload(@RequestPart("file") MultipartFile file,
                                 @RequestParam("bucket") String bucket) {
        if (bucket.length() < 3) {
            StringBuilder newBucket = new StringBuilder();
            for (int i = bucket.length(); i < 3; i++) {
                newBucket.append("0");
            }
            bucket = newBucket + bucket;
        }
        return ResponseEntity.ok(minioService.putObject(file, bucket));
    }

    @GetMapping(path = "/url/{bucket}/{hash}")
    public ResponseEntity url(@PathVariable("bucket") String bucket, @PathVariable("hash") String hash) {
        return ResponseEntity.ok(minioService.getObjectUrl(bucket, hash));
    }

}
