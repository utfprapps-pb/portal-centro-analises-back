package com.portal.centro.API.configuration;

import com.portal.centro.API.model.User;
import com.portal.centro.API.security.AuthenticationToken;
import com.portal.centro.API.security.AuthenticationTokenDetails;
import com.portal.centro.API.service.UserService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {

    private final UserService userService;

    public AuditorAwareImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            AuthenticationToken token = (AuthenticationToken) authentication;
            AuthenticationTokenDetails details = (AuthenticationTokenDetails) token.getDetails();
            return Optional.of(User.builder()
                    .id(details.getId())
                    .build());
        } catch (ClassCastException e) {
            return Optional.empty();
        }


    }

}
