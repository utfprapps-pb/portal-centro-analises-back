package com.portal.centro.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "finance_id")
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private Finance finance;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_hash")
    private String fileHash;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "bucket")
    private String bucket;

    @Column(name = "index")
    private Long index;

    @Transient
    private String url;

}
