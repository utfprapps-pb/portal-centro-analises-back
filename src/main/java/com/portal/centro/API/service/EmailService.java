package com.portal.centro.API.service;

import com.portal.centro.API.dto.EmailDto;
import com.portal.centro.API.model.EmailConfig;
import com.portal.centro.API.utils.EmailMessageGenerator;
import com.portal.centro.API.utils.UtilsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class EmailService {

    private final UtilsService utilsService;
    private final EmailConfigService emailConfigService;
    private final EmailMessageGenerator emailMessageGenerator;

    public EmailService (UtilsService utils, EmailConfigService emailConfigService, EmailMessageGenerator emailMessageGenerator) {
        this.utilsService = utils;
        this.emailConfigService = emailConfigService;
        this.emailMessageGenerator = emailMessageGenerator;
    }

    public void sendEmail(EmailDto emailTo) throws Exception {
        this.emailConfigService.validateIfExistsEmailConfig();
        HtmlEmail htmlEmail = new HtmlEmail();
        try{
            EmailConfig emailConfig = emailConfigService.find();
            htmlEmail.setHostName(emailConfig.getHost());
            htmlEmail.setSmtpPort(emailConfig.getPort());
            htmlEmail.setAuthenticator(new DefaultAuthenticator(emailConfig.getEmail(), emailConfig.getPassword()));
            htmlEmail.setStartTLSEnabled(true);
            htmlEmail.setFrom(emailConfig.getEmail());
            htmlEmail.addTo(emailTo.getEmailTo());
            htmlEmail.setSubject(emailTo.getSubject());
            htmlEmail.setHtmlMsg(emailMessageGenerator.generateHTML(emailTo.getSubjectBody(), emailTo.getContentBody(), ""));

            log.info("enviando email....");
            htmlEmail.send();
            log.info("email enviado com sucesso!");

        } catch (Exception e) {
            log.error("Email Service -> sendEmail(): Erro ao enviar email:" + e.getMessage());
            e.printStackTrace();
        }

    }
}