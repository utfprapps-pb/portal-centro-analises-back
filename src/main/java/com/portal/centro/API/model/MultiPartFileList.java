package com.portal.centro.API.model;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class MultiPartFileList extends IModel {

    private String fileName;

    private String contentType;

//    @ManyToOne
//    private TechnicalReport technicalReport;
}
