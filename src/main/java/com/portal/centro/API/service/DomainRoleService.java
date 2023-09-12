package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.DomainRole;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.DomainRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DomainRoleService extends GenericService<DomainRole, Long> {

    private final UserService userService;

    public DomainRoleService(DomainRoleRepository domainRoleRepository, UserService userService) {
        super(domainRoleRepository);
        this.userService = userService;
    }

    @Override
    public DomainRole save(DomainRole requestBody) throws Exception {
        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ADMIN))
            throw new ValidationException("Somente o administrador pode realizar esta ação.");

        return super.save(requestBody);
    }
}
