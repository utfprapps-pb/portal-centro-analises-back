package br.edu.utfpr.pb.app.labcaapi.security;

import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.repository.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        if (user.getStatus() != StatusInactiveActive.ACTIVE) {
            throw new DisabledException("Sua conta está inativa. Entre em contato com o administrador.");
        }

        if (user.getEmailVerified() == null || !user.getEmailVerified()) {
            throw new DisabledException("Seu email ainda não foi verificado. Confirme seu endereço de email.");
        }

        return user;
    }

    public User findLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.toString());
    }

}
