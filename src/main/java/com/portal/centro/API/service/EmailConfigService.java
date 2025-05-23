package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.EmailConfig;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.EmailConfigRepository;
import com.portal.centro.API.security.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

@Service
public class EmailConfigService extends GenericService<EmailConfig, Long> {

    final EmailConfigRepository emailConfigRepository;
    final AuthService authService;

    public EmailConfigService(EmailConfigRepository emailConfigRepository, AuthService authService) {
        super(emailConfigRepository);
        this.emailConfigRepository = emailConfigRepository;
        this.authService = authService;
    }

    public EmailConfig find() throws Exception {
        List<EmailConfig> emailConfigList = emailConfigRepository.findAll();
        if (ObjectUtils.isEmpty(emailConfigList)) {
            return new EmailConfig();
        }
        return emailConfigList.get(0);
    }

    public void validateIfExistsEmailConfig() throws Exception {
        List<EmailConfig> emailConfigList = emailConfigRepository.findAll();
        if (ObjectUtils.isEmpty(emailConfigList)) {
            throw new GenericException("Configuração de email não encontrada.");
        }
    }

    @Override
    public EmailConfig save(EmailConfig requestBody) throws Exception {
        User selfUser = authService.findLoggedUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN))
            throw new ValidationException("Somente o administrador pode realizar esta ação.");
        validateExistenceJustOneConfigEmail(requestBody);
        return super.save(requestBody);
    }

    private void validateExistenceJustOneConfigEmail(EmailConfig requestBody) {
        List<EmailConfig> existingEmailConfigs = emailConfigRepository.findAll();
        if (existingEmailConfigs.isEmpty())
            return;
        EmailConfig existingEmailConfig = existingEmailConfigs.get(0);
        if (Objects.nonNull(existingEmailConfig) && (!Objects.equals(existingEmailConfig.getId(), requestBody.getId())))
            throw new ValidationException("Não é possível incluir novas configurações de email, apenas uma. Para realizar esta ação atualize a existente de código " + existingEmailConfig.getId() + ".");
    }

}
