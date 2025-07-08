package br.edu.utfpr.pb.app.labcaapi.configuration;

import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    private final UserService userService;

    public JpaAuditingConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new AuditorAwareImpl(userService);
    }
}