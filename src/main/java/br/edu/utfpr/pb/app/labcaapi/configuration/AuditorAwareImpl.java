package br.edu.utfpr.pb.app.labcaapi.configuration;

import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.security.AuthenticationToken;
import br.edu.utfpr.pb.app.labcaapi.security.AuthenticationTokenDetails;
import br.edu.utfpr.pb.app.labcaapi.service.UserService;
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
