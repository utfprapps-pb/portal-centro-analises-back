package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.model.RecoverPassword;
import br.edu.utfpr.pb.app.labcaapi.utils.DateTimeUtil;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RecoverPasswordService {

    private static final Integer MAX_MINUTES_VALID_CODE = 30;

    @Getter
    private Map<String, RecoverPassword> codeSentByEmail = new ConcurrentHashMap<>();

    public void addCode(String email, RecoverPassword recoverPassword) {
        this.codeSentByEmail.put(email, recoverPassword);
    }

    @Scheduled(fixedRate = 1800000)
    public void clearExpiredCodesSentByEmail() {
        codeSentByEmail.values().removeIf(this::codeExpired);
    }

    public Boolean codeExpired(RecoverPassword recoverPassword) {
        if (recoverPassword == null || recoverPassword.getDateTime() == null) {
            return true;
        }
        return Duration.between(recoverPassword.getDateTime(), DateTimeUtil.getCurrentDateTime()).toMinutes() >= MAX_MINUTES_VALID_CODE;
    }

    public boolean verifyAndConsumeCode(String email, Integer inputCode) {
        RecoverPassword stored = codeSentByEmail.get(email);
        if (stored == null || codeExpired(stored)) {
            return false;
        }

        boolean matches = Objects.equals(stored.getCode(), inputCode);
        if (matches) {
            codeSentByEmail.remove(email);
        }
        return matches;
    }

}
