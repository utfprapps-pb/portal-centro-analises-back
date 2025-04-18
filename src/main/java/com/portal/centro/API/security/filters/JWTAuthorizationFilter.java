package com.portal.centro.API.security.filters;

import cn.hutool.json.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.portal.centro.API.model.User;
import com.portal.centro.API.security.AuthenticationToken;
import com.portal.centro.API.security.AuthenticationTokenDetails;
import com.portal.centro.API.security.SecurityConstants;
import com.portal.centro.API.security.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthService authService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  AuthService authService) {
        super(authenticationManager);
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String path = request.getRequestURI();

        // Ignora o WebSocket e qualquer outra rota pública
        if (path.startsWith("/api/ws") || path.startsWith("/ws") || path.startsWith("/wss")) {
            chain.doFilter(request, response);
            return;
        }
        
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String headerUser = request.getHeader(SecurityConstants.HEADER_USER_STRING);
        byte[] decoded = Base64.getDecoder().decode(headerUser);
        String decodedStr = new String(decoded, StandardCharsets.ISO_8859_1);
        JSONObject json = new JSONObject(decodedStr);

        AuthenticationToken authenticationToken = getAuthentication(request);

        assert authenticationToken != null;
        if (authenticationToken.getAuthorities().stream().noneMatch(it -> it.getAuthority().equals(json.get("role")))) {
            throw new RuntimeException("mapped|GenericException|" + "Usuário inválido!");
        }

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    private AuthenticationToken getAuthentication(HttpServletRequest request) throws TokenExpiredException {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            String email =
                    JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET))
                            .build()
                            .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                            .getSubject();
            if (email != null) {
                User user = (User) authService.loadUserByUsername(email);
                AuthenticationToken authToken = new AuthenticationToken(email, null, user.getAuthorities());
                AuthenticationTokenDetails authTokenDetails = new AuthenticationTokenDetails();
                authTokenDetails.setId(user.getId());
                authToken.setDetails(authTokenDetails);
                return authToken;
            }
        }
        return null;
    }
}
