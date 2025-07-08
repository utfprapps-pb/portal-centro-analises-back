package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.DomainRole;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.repository.DomainRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DomainRoleService extends GenericService<DomainRole, Long> {

    private final UserService userService;
    private final DomainRoleRepository domainRoleRepository;

    public DomainRoleService(DomainRoleRepository domainRoleRepository, UserService userService, DomainRoleRepository domainRoleRepository1) {
        super(domainRoleRepository);
        this.userService = userService;
        this.domainRoleRepository = domainRoleRepository1;
    }

    @Override
    public DomainRole save(DomainRole requestBody) throws Exception {
        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            throw new GenericException("Somente o administrador pode realizar esta ação.");
        }
        validJustOneDomain(requestBody);
        return super.save(requestBody);
    }

    private void validJustOneDomain(DomainRole requestBody) throws Exception {
        Optional<DomainRole> domainRoleOptional = domainRoleRepository.findByDomain(requestBody.getDomain());
        if (domainRoleOptional.isEmpty() || Objects.equals(requestBody.getId(), domainRoleOptional.get().getId()))
            return;
        throw new GenericException("O domínio informado já existe. Por favor, informe outro.");
    }

}
