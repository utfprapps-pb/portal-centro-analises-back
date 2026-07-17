package br.edu.utfpr.pb.app.labcaapi;

import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.repository.UserRepository;
import br.edu.utfpr.pb.app.labcaapi.security.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = User.builder()
                .id(1L)
                .email("user@utfpr.edu.br")
                .status(StatusInactiveActive.ACTIVE)
                .emailVerified(true)
                .build();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // LoadUserByUsername

    @Test
    @DisplayName("Deve carregar UserDetails com sucesso para usuário ativo e verificado")
    void loadUserByUsername_Success() {
        when(userRepository.findByEmail("user@utfpr.edu.br")).thenReturn(validUser);

        UserDetails userDetails = authService.loadUserByUsername("user@utfpr.edu.br");

        assertNotNull(userDetails);
        assertEquals("user@utfpr.edu.br", userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail("user@utfpr.edu.br");
    }

    @Test
    @DisplayName("Deve lançar UsernameNotFoundException quando usuário não for encontrado")
    void loadUserByUsername_ThrowsUsernameNotFoundException() {
        when(userRepository.findByEmail("notfound@utfpr.edu.br")).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> authService.loadUserByUsername("notfound@utfpr.edu.br")
        );

        assertEquals("Usuário não encontrado!", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("notfound@utfpr.edu.br");
    }

    @Test
    @DisplayName("Deve lançar DisabledException quando usuário estiver inativo")
    void loadUserByUsername_ThrowsDisabledException_WhenInactive() {
        validUser.setStatus(StatusInactiveActive.INACTIVE);
        when(userRepository.findByEmail("user@utfpr.edu.br")).thenReturn(validUser);

        DisabledException exception = assertThrows(
                DisabledException.class,
                () -> authService.loadUserByUsername("user@utfpr.edu.br")
        );

        assertEquals("Sua conta está inativa. Entre em contato com o administrador.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar DisabledException quando email não estiver verificado")
    void loadUserByUsername_ThrowsDisabledException_WhenEmailNotVerified() {
        validUser.setEmailVerified(false);
        when(userRepository.findByEmail("user@utfpr.edu.br")).thenReturn(validUser);

        DisabledException exception = assertThrows(
                DisabledException.class,
                () -> authService.loadUserByUsername("user@utfpr.edu.br")
        );

        assertEquals("Seu email ainda não foi verificado. Confirme seu endereço de email.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar DisabledException quando flag de email verificado for nula")
    void loadUserByUsername_ThrowsDisabledException_WhenEmailVerifiedIsNull() {
        validUser.setEmailVerified(null);
        when(userRepository.findByEmail("user@utfpr.edu.br")).thenReturn(validUser);

        assertThrows(DisabledException.class, () -> authService.loadUserByUsername("user@utfpr.edu.br"));
    }

    // FindLoggedUser

    @Test
    @DisplayName("Deve retornar o usuário logado extraindo o email do SecurityContext")
    void findLoggedUser_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("user@utfpr.edu.br");
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail("user@utfpr.edu.br")).thenReturn(validUser);

        User loggedUser = authService.findLoggedUser();

        assertNotNull(loggedUser);
        assertEquals("user@utfpr.edu.br", loggedUser.getEmail());
        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getPrincipal();
    }
}
