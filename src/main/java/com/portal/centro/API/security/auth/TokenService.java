package com.portal.centro.API.security.auth;

import java.io.Serializable;
import com.portal.centro.API.security.SecurityConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TokenService implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    public boolean validateJwtToken(String authToken) {
        try {
            String parsedToken = authToken.replaceAll("Bearer ", "");
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(parsedToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}