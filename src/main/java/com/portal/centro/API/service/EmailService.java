package com.portal.centro.API.service;

import com.portal.centro.API.dto.EmailDto;
import com.portal.centro.API.model.ConfigEmail;
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
    private final ConfigEmailService configEmailService;
    private final EmailMessageGenerator emailMessageGenerator;

    public EmailService (UtilsService utils, ConfigEmailService configEmailService, EmailMessageGenerator emailMessageGenerator) {
        this.utilsService = utils;
        this.configEmailService = configEmailService;
        this.emailMessageGenerator = emailMessageGenerator;
    }

    public void sendEmail(EmailDto emailTo) throws Exception {
        this.configEmailService.validateIfExistsEmailConfig();
        HtmlEmail htmlEmail = new HtmlEmail();
        try{
            ConfigEmail configEmail = configEmailService.find();
            htmlEmail.setHostName(configEmail.getSendHost());
            htmlEmail.setSmtpPort(configEmail.getSendPort());
            htmlEmail.setAuthenticator(new DefaultAuthenticator(configEmail.getEmailFrom(), configEmail.getPasswordEmailFrom()));
            htmlEmail.setStartTLSEnabled(true);
            htmlEmail.setFrom(configEmail.getEmailFrom());
            htmlEmail.addTo(emailTo.getEmailTo());
            htmlEmail.setSubject(emailTo.getSubject());
            htmlEmail.setHtmlMsg(emailMessageGenerator.generateHTML(emailTo.getSubjectBody(), emailTo.getContentBody(), ""));

            log.info("enviando email....");
            htmlEmail.send();
            log.info("email enviado com sucesso!");

        } catch (Exception e) {
            log.info("erro ao enviar email....");
            e.printStackTrace();
        }

    }
}