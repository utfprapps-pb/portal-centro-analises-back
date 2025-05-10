package com.portal.centro.API.service;

import com.portal.centro.API.configuration.ApplicationContextProvider;
import com.portal.centro.API.dto.EmailDto;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.model.EmailConfig;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.User;
import com.portal.centro.API.utils.EmailMessageGenerator;
import com.portal.centro.API.utils.UtilsService;
import org.springframework.transaction.annotation.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
@Slf4j
public class EmailService {

    private final UtilsService utilsService;
    private final EmailConfigService emailConfigService;
    private final EmailMessageGenerator emailMessageGenerator;

    public EmailService(UtilsService utils, EmailConfigService emailConfigService, EmailMessageGenerator emailMessageGenerator) {
        this.utilsService = utils;
        this.emailConfigService = emailConfigService;
        this.emailMessageGenerator = emailMessageGenerator;
    }

    public void sendEmail(EmailDto emailTo) throws Exception {
        this.emailConfigService.validateIfExistsEmailConfig();
        HtmlEmail htmlEmail = new HtmlEmail();
        try {
            EmailConfig emailConfig = emailConfigService.find();
            htmlEmail.setHostName(emailConfig.getHost());
            htmlEmail.setSmtpPort(emailConfig.getPort());
            htmlEmail.setAuthenticator(new DefaultAuthenticator(emailConfig.getEmail(), emailConfig.getPassword()));
            htmlEmail.setStartTLSEnabled(true);
            htmlEmail.setFrom(emailConfig.getEmail());
            htmlEmail.addTo(emailTo.getEmailTo());
            htmlEmail.setSubject("Lab. Central - " + emailTo.getSubject());
            htmlEmail.setHtmlMsg(emailMessageGenerator.generateHTML(emailTo.getSubjectBody(), emailTo.getContentBody(), ""));

            log.info("Enviando email....");
//            htmlEmail.send();
            log.info("E-mail enviado com sucesso!");
        } catch (Exception e) {
            log.error("E-mail Service -> sendEmail(): Erro ao enviar email:" + e.getMessage());
            e.printStackTrace();
            throw new GenericException("Erro ao enviar email");
        }

    }

    @Transactional
    public void sendUpdateSolicitationStatus(Solicitation solicitation, SolicitationStatus newStatus, SolicitationStatus oldStatus, String obs) throws Exception {
        this.emailConfigService.validateIfExistsEmailConfig();

        List<String> emails = new ArrayList<>();
        if (solicitation.getCreatedBy() != null) {
            if (!solicitation.getCreatedBy().getRole().equals(Type.ROLE_ADMIN)) {
                emails.add(solicitation.getCreatedBy().getEmail());
            }
        }
        if (solicitation.getResponsavel() != null) {
            emails.add(solicitation.getResponsavel().getEmail());
        }

        Map<String, EmailDto> mapUserEmail = new HashMap<>();

        for (String email : emails) {
            EmailDto emailDto = mapUserEmail.get(email);
            if (emailDto == null) {
                emailDto = new EmailDto();
                emailDto.setEmailTo(email);
                mapUserEmail.put(email, emailDto);
            }
            emailDto.setEmailTo(email);
            emailDto.setSubject("Atualização de solicitação");
            String contentBody = this.emailMessageGenerator.generateHTML(
                    "Atualização de solicitação",
                    "A solicitação de <strong>Código " + solicitation.getId() + "</strong> teve seu <strong>status</strong> alterado<br>" +
                            "De: <strong>" + oldStatus.getContent() + "</strong><br>" +
                            "Para: <strong>" + newStatus.getContent() + "</strong>.", null);

            if (ObjectUtils.isNotEmpty(obs)) {
                contentBody += "<br>Observação: " + obs;
            }
            emailDto.setContentBody(contentBody);
        }

        for (EmailDto emailDto : mapUserEmail.values()) {
            this.sendEmail(emailDto);
        }
    }

    @Transactional
    public void sendEmailProjectResponsible(Solicitation solicitation) throws Exception {
        this.emailConfigService.validateIfExistsEmailConfig();
        UserService userService = ApplicationContextProvider.getBean(UserService.class);
        User responsavel = userService.findOneById(solicitation.getResponsavel().getId());

        EmailDto emailDto = new EmailDto();
        emailDto.setEmailTo(responsavel.getEmail());
        emailDto.setSubject("Solicitação de autorização de formulário");
        String contentBody = this.emailMessageGenerator.generateHTML(
                "Solicitação de autorização de formulário",
                "Solicitação <strong>Código " + solicitation.getId() + "</strong><br>" +
                        "Solicitação " + solicitation.getId() + " está aguardando sua autorização para avançar."
                , null);

        emailDto.setContentBody(contentBody);

        this.sendEmail(emailDto);
    }
}
