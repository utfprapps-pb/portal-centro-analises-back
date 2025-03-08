package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Attachment;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends GenericRepository<Attachment, Long> {

}
