package com.portal.centro.API.security;

import cn.hutool.json.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.portal.centro.API.configuration.ApplicationContextProvider;
import com.portal.centro.API.model.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {

        URI uri = request.getURI();
        String query = uri.getQuery();

        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] kv = param.split("=");
                if (kv.length == 2) {
                    attributes.put(kv[0], kv[1]);
                }
            }
        }

        if (!attributes.isEmpty()) {
            String token = (String) attributes.get("token");
            String credential = (String) attributes.get("credentials");

            if (token == null || credential == null) {
                return false;
            } else {
                byte[] decoded = Base64.getDecoder().decode(credential);
                String decodedStr = new String(decoded, StandardCharsets.ISO_8859_1);
                JSONObject json = new JSONObject(decodedStr);

                AuthenticationToken authenticationToken = getAuthentication(token);

                assert authenticationToken != null;
                if (authenticationToken.getAuthorities().stream().noneMatch(it -> it.getAuthority().equals(json.get("role")))) {
                    return false;
                }

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authenticationToken);
                return true;
            }
        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private AuthenticationToken getAuthentication(String token) throws TokenExpiredException {
        if (token != null) {
            String email =
                    JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET))
                            .build()
                            .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                            .getSubject();
            if (email != null) {
                User user = (User) ApplicationContextProvider.getBean(AuthService.class).loadUserByUsername(email);
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

