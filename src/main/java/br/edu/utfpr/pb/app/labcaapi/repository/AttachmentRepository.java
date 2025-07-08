package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Attachment;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends GenericRepository<Attachment, Long> {

}
