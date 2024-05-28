package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Partner;
import com.portal.centro.API.repository.PartnerRepository;
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


