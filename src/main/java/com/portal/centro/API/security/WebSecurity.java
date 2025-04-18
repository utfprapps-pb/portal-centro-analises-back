package com.portal.centro.API.security;

import com.portal.centro.API.security.filters.JWTAuthenticationFilter;
import com.portal.centro.API.security.filters.JWTAuthorizationFilter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurity {

    private final AuthService authService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurity(AuthService authService,
                       AuthenticationEntryPoint authenticationEntryPoint) {
        this.authService = authService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authService)
                .passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> corsConfigurationSource());

        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint));
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(antMatcher("/wss/**")).permitAll()
                .requestMatchers(antMatcher("/ws/**")).permitAll()

                .requestMatchers(antMatcher(HttpMethod.GET, "/email-confirm/**")).permitAll()
                .requestMatchers(antMatcher("/open/**")).permitAll()

                .requestMatchers(antMatcher("/usuarios/**")).authenticated()

                .requestMatchers(antMatcher(HttpMethod.GET, "/termos-de-uso")).authenticated()
                .requestMatchers(antMatcher("/termos-de-uso/**")).hasRole("ADMIN")

                .requestMatchers(
                        antMatcher("/vinculos"),
                        antMatcher("/vinculos/**")
                ).authenticated()

                .requestMatchers(antMatcher("/solicitar/save")).authenticated()
                .requestMatchers(antMatcher("/solicitar/**")).denyAll()

                .requestMatchers(
                        antMatcher("/solicitacoes/atualizar-status"),
                        antMatcher("/solicitacoes/salvar/solicitacao-amostra-analise"),
                        antMatcher("/solicitacoes/salvar/solicitacao-amostra-finalizar")
                ).hasRole("ADMIN")
                .requestMatchers(
                        antMatcher("/solicitacoes"),
                        antMatcher("/solicitacoes/**")
                ).authenticated()

                .requestMatchers(
                        antMatcher("/projetos"),
                        antMatcher("/projetos/**")
                ).authenticated()

                .requestMatchers(antMatcher("/parceiros/**")).hasRole("ADMIN")
                .requestMatchers(antMatcher("/minio/**")).authenticated()

                .requestMatchers(antMatcher("/equipamentos")).authenticated()
                .requestMatchers(antMatcher("/equipamentos/**")).hasRole("ADMIN")

                .requestMatchers(antMatcher("/email-config/**")).hasRole("ADMIN")
                .requestMatchers(antMatcher("/dominios/**")).hasRole("ADMIN")

                        .requestMatchers(antMatcher("/email/config/**")).hasRole("ADMIN")

                .anyRequest().permitAll()
        );
        http.authenticationManager(authenticationManager)
                .addFilter(new JWTAuthenticationFilter(authenticationManager, authService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager, authService))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

//        configuration.setAllowedOrigins(List.of("*"));
//                configuration.setAllowedOrigins(List.of("http://127.0.0.1:5173"));
//                configuration..setAllowedOrigins(List.of("http://localhost:5173"));
        configuration..setAllowedOrigins(List.of("https://ca-dev.app.pb.utfpr.edu.br"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        configuration.setAllowedHeaders(List.of("Authorization", "x-xsrf-token",
                "Access-Control-Allow-Headers", "Origin",
                "Accept", "X-Requested-With", "Content-Type",
                "Access-Control-Request-Method", "Credentials",
                "Access-Control-Request-Headers", "Auth-Id-Token"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
