package br.edu.utfpr.pb.app.labcaapi.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailMessageGenerator {

    @Autowired
    private TemplateEngine templateEngine;

    public String generateHTML(String subject, String content, String url) {
        Context context = new Context();
        context.setVariables(DataToMap(subject, content, url));

        return templateEngine.process("emailtemplate", context);
    }

    public Map<String, Object> DataToMap(String subject, String content, String url) {
        Map<String, Object> map = new HashMap<>();
        map.put("subject", subject);
        map.put("content", content);
        map.put("url", url);
        return map;
    }
}