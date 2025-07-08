package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.model.RecoverPassword;
import br.edu.utfpr.pb.app.labcaapi.utils.DateTimeUtil;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecoverPasswordService {

    private static final String EVERY_MID_NIGHT = "0 0 0 * * ?";
    private static final Integer MAX_MINUTES_VALID_CODE = 30;

    @Getter
    private Map<String, RecoverPassword> codeSentByEmail = new HashMap<>();

    public void addCode(String email, RecoverPassword recoverPassword) {
        this.codeSentByEmail.put(email, recoverPassword);
    }

    @Scheduled(cron = EVERY_MID_NIGHT)
    public void clearExpiredCodesSentByEmail() {
        for (RecoverPassword recoverPassword : codeSentByEmail.values()) {
            if (codeExpired(recoverPassword))
                codeSentByEmail.remove(recoverPassword.getEmail());
        }
    }

    public Boolean codeExpired(RecoverPassword recoverPassword) {
        return (Duration.between(recoverPassword.getDateTime(), DateTimeUtil.getCurrentDateTime()).toMinutes() >= MAX_MINUTES_VALID_CODE);
    }

}
