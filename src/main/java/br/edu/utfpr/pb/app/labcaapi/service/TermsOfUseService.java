package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.TermsOfUse;
import br.edu.utfpr.pb.app.labcaapi.repository.TermsOfUseRepository;
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
