package br.edu.utfpr.pb.app.labcaapi.provider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ca.front")
public class ConfigFrontProvider {

    private String baseurl;
    private String emailconfirm;
    private Integer port;
}
