package com.portal.centro.API.service;

import com.portal.centro.API.dto.EmailDto;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.EmailCode;
import com.portal.centro.API.model.User;
import com.portal.centro.API.provider.ConfigFrontProvider;
import com.portal.centro.API.repository.EmailCodeRepository;
import com.portal.centro.API.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Service
public class EmailCodeService extends GenericService<EmailCode, Long> {
    private final HashingService hashingService;
    private final EmailService emailService;
    private final EmailCodeRepository emailCodeRepository;
    private final UserRepository userRepository;
    private final EmailConfigService emailConfigService;

    @Value("${ca.back.baseurl}")
    private String backBaseURL;

    @Autowired
    public EmailCodeService(EmailCodeRepository emailCodeRepository,
                            HashingService hashingService,
                            UserRepository userRepository,
                            EmailService emailService,
                            EmailConfigService emailConfigService) {
        super(emailCodeRepository);
        this.hashingService = hashingService;
        this.emailCodeRepository = emailCodeRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.emailConfigService = emailConfigService;
    }

    @Override
    public EmailCode save(EmailCode emailCode) throws Exception {
        return super.save(emailCode);
    }

    @Transactional
    public EmailCode createCode(User user) throws Exception {
        this.emailConfigService.validateIfExistsEmailConfig();
        LocalDateTime dateTime = LocalDateTime.now();
        String hashKey = this.hashingService.generateHashKey(user.getEmail() + dateTime);

        EmailCode emailCode = EmailCode.builder().code(hashKey).user(user).createdAt(dateTime).build();

        //ENVIO DE EMAIL
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Confirmação de email");
        emailDto.setSubjectBody("Codigo para confirmação");
        String link = backBaseURL + "/email-confirm/" + hashKey;

        emailDto.setContentBody("<p>Clique aqui para confirmar seu email: <a href=" + link + ">CONFIRMAR</a>.</p></br>Caso nao consiga clicar, copie e cole a URl abaixo no seu navegador: " + link);

        emailService.sendEmail(emailDto);
        return this.save(emailCode);
    }

    public boolean confirmEmail(String hash) throws Exception {
        boolean isSuccess = false;

        LocalDateTime dateTime = LocalDateTime.now();

        EmailCode emailCode = this.emailCodeRepository.findEmailCodeByCode(hash);
        if (emailCode != null) {
            User user = emailCode.getUser();
            user.setEmailVerified(true);
            userRepository.save(user);
            emailCode.setValidateAt(dateTime);

            save(emailCode);
            isSuccess = true;
        }

        return isSuccess;
    }
}

