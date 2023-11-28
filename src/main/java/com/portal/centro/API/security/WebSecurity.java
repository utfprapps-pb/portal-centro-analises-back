package com.portal.centro.API.security;

import com.portal.centro.API.security.auth.AuthService;
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
                .passwordEncoder( passwordEncoder() );
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> corsConfigurationSource());

        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint));
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(antMatcher(HttpMethod.POST,"/users/**")).permitAll()
                .requestMatchers(antMatcher("/error/**")).permitAll()
                .requestMatchers(antMatcher("/errors/**")).permitAll()
                .requestMatchers(antMatcher(HttpMethod.GET, "/emailconfirm/**")).permitAll()
                .requestMatchers(antMatcher(HttpMethod.POST, "/emailconfirm/**")).permitAll()
                .requestMatchers(antMatcher("/v3/**")).permitAll()
                .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(antMatcher("/admin/**")).hasRole("ADMIN")

                .requestMatchers(antMatcher(HttpMethod.POST, "/equipments/**")).hasAnyRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.PUT, "/equipments/**")).hasAnyRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.DELETE, "/equipments/**")).hasAnyRole("ADMIN")

                .requestMatchers(antMatcher(HttpMethod.GET,"/partners/**")).hasAnyRole("ADMIN")

                .requestMatchers(antMatcher(HttpMethod.POST, "/solicitation/approve/**")).hasAnyRole("PROFESSOR")
                .requestMatchers(antMatcher(HttpMethod.POST, "/solicitation/approvelab/**")).hasAnyRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.GET, "/solicitation/pendingpage")).hasAnyRole("ADMIN", "PROFESSOR")
                .requestMatchers(antMatcher(HttpMethod.GET, "/solicitation/pending")).hasAnyRole("ADMIN", "PROFESSOR")

                .requestMatchers(antMatcher(HttpMethod.POST, "/transaction")).hasAnyRole("ADMIN")

                .requestMatchers(antMatcher(HttpMethod.DELETE, "/users")).hasAnyRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.GET, "/users/pagestatus")).hasAnyRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.GET, "/users")).hasAnyRole("ADMIN", "PROFESSOR")
                .requestMatchers(antMatcher(HttpMethod.GET, "/users/findInactive")).hasAnyRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.PUT, "/users/activatedUser/**")).hasAnyRole("ADMIN")
                .anyRequest().authenticated()
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
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
//                configuration.setAllowedOrigins(List.of("http://127.0.0.1:5173"));
//                configuration..setAllowedOrigins(List.of("http://localhost:5173"));
//                configuration..setAllowedOrigins(List.of("https://ca-dev.app.pb.utfpr.edu.br/"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        configuration.setAllowedHeaders(List.of("Authorization","x-xsrf-token",
                "Access-Control-Allow-Headers", "Origin",
                "Accept", "X-Requested-With", "Content-Type",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers", "Auth-Id-Token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
