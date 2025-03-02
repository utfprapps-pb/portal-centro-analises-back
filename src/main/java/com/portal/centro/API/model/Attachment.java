package com.portal.centro.API.model;

import com.portal.centro.API.configuration.ApplicationContextProvider;
import com.portal.centro.API.generic.base.IModel;
import com.portal.centro.API.minio.service.impl.MinioServiceImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tb_attachment")
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment extends IModel {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_hash")
    private String fileHash;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "url", columnDefinition = "text")
    private String url;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "bucket")
    private String bucket;

    @Column(name = "index")
    private Long index;

}
