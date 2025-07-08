package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.exceptions.ValidationException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.EmailConfig;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.repository.EmailConfigRepository;
import br.edu.utfpr.pb.app.labcaapi.security.AuthService;
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
