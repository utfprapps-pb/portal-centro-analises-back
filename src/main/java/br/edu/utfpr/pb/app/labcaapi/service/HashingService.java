package br.edu.utfpr.pb.app.labcaapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Map;

@Component
public class HashingService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateHashKey(String username) {
        LocalDate date = LocalDate.now();
        String key = date.toString() + "-" + username;

        MessageDigest digest = new SHA3.Digest256();
        byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
        String hashKey = Hex.toHexString(hash);

        return hashKey;
    }

    public String handleHash(String data, String hashKey) throws Exception {
        Map<String, Object> input = objectMapper.readValue(data, Map.class);
        input.put("hashKey", hashKey);

        String jsonString = objectMapper.writeValueAsString(input);
        MessageDigest digest = new SHA3.Digest256();
        byte[] hash = digest.digest(jsonString.getBytes(StandardCharsets.UTF_8));
        String hashedData = Hex.toHexString(hash);

        return hashedData;
    }

}


