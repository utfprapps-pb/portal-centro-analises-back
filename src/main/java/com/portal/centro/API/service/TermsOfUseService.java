package com.portal.centro.API.service;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.model.TermsOfUse;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.SolicitationHistoricRepository;
import com.portal.centro.API.repository.TermsOfUseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TermsOfUseService extends GenericService<TermsOfUse, Long> {

    private final TermsOfUseRepository termsOfUseRepository;

    public TermsOfUseService(TermsOfUseRepository termsOfUseRepository, UserService userService) {
        super(termsOfUseRepository);
        this.termsOfUseRepository = termsOfUseRepository;
    }

}
