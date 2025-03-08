package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.SolicitationAttachments;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitationAttachmentsRepository extends GenericRepository<SolicitationAttachments, Long> {

    SolicitationAttachments findByAttachment_Id(Long id);

}