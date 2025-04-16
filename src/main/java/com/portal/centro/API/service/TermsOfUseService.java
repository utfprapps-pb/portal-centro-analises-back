package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.TermsOfUse;
import com.portal.centro.API.repository.TermsOfUseRepository;
import org.springframework.stereotype.Service;

@Service
public class TermsOfUseService extends GenericService<TermsOfUse, Long> {

    private final TermsOfUseRepository termsOfUseRepository;
    private final UserService userService;

    public TermsOfUseService(TermsOfUseRepository termsOfUseRepository, UserService userService) {
        super(termsOfUseRepository);
        this.termsOfUseRepository = termsOfUseRepository;
        this.userService = userService;
    }

}
