package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.Partner;
import br.edu.utfpr.pb.app.labcaapi.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService extends GenericService<Partner, Long> {

    private final PartnerRepository repository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository) {
        super(partnerRepository);
        this.repository = partnerRepository;
    }

    public List<String> findAllEstudantes() {
        return repository.findAllEstudantes();
    }
}


