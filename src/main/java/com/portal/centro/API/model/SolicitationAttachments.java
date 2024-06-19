package com.portal.centro.API.model;

import com.portal.centro.API.enums.SolicitationFileType;
import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_solicitation_attachments")
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitationAttachments extends IModel {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "url")
    private String url;

    @Enumerated(value = EnumType.STRING)
    private SolicitationFileType attachments;

}
