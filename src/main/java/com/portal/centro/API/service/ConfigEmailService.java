package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.ConfigEmail;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.ConfigEmailRepository;
import com.portal.centro.API.security.auth.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ConfigEmailService extends GenericService<ConfigEmail, Long> {

    final ConfigEmailRepository configEmailRepository;
    final AuthService authService;

    public ConfigEmailService(ConfigEmailRepository configEmailRepository, AuthService authService) {
        super(configEmailRepository);
        this.configEmailRepository = configEmailRepository;
        this.authService = authService;
    }

    public ConfigEmail find() {
        List<ConfigEmail> configEmailList = configEmailRepository.findAll();
        if (configEmailList.isEmpty())
            throw new ValidationException("Configuração de email não encontrada.");
        return configEmailList.get(0);
    }

    @Override
    public ConfigEmail save(ConfigEmail requestBody) throws Exception {
        User selfUser = authService.findLoggedUser();
        if (!Objects.equals(selfUser.getRole(), Type.ADMIN))
            throw new ValidationException("Somente o administrador pode realizar esta ação.");
        validateExistenceJustOneConfigEmail(requestBody);
        return super.save(requestBody);
    }

    private void validateExistenceJustOneConfigEmail(ConfigEmail requestBody) {
        List<ConfigEmail> existingConfigEmails = configEmailRepository.findAll();
        if (existingConfigEmails.isEmpty())
            return;
        ConfigEmail existingConfigEmail = existingConfigEmails.get(0);
        if (Objects.nonNull(existingConfigEmail) && (!Objects.equals(existingConfigEmail.getId(), requestBody.getId())))
            throw new ValidationException("Não é possível incluir novas configurações de email, apenas uma. Para realizar esta ação atualize a existente de código " + existingConfigEmail.getId() + ".");
    }

}
