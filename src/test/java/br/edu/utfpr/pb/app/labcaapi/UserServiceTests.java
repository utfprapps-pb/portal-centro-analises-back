package br.edu.utfpr.pb.app.labcaapi;

import br.edu.utfpr.pb.app.labcaapi.configuration.ApplicationContextProvider;
import br.edu.utfpr.pb.app.labcaapi.dto.ChangePasswordDTO;
import br.edu.utfpr.pb.app.labcaapi.dto.RecoverPasswordDTO;
import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.model.RecoverPassword;
import br.edu.utfpr.pb.app.labcaapi.model.SendEmailCodeRecoverPassword;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.model.UserBalance;
import br.edu.utfpr.pb.app.labcaapi.repository.UserRepository;
import br.edu.utfpr.pb.app.labcaapi.responses.DefaultResponse;
import br.edu.utfpr.pb.app.labcaapi.service.*;
import br.edu.utfpr.pb.app.labcaapi.utils.UtilsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UtilsService utilsService;
    @Mock
    private RecoverPasswordService recoverPasswordService;
    @Mock
    private EmailCodeService emailCodeService;
    @Mock
    private EmailService emailService;
    @Mock
    private EmailConfigService emailConfigService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private User createValidUser(Long id, String name, String email, String cpfCnpj, Type role, String password) {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .cpfCnpj(cpfCnpj)
                .role(role)
                .status(StatusInactiveActive.ACTIVE)
                .build();
    }

    private void mockSecurityContext(String principalEmail) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principalEmail);
        SecurityContextHolder.setContext(securityContext);
    }

    @Nested
    @DisplayName("Testes de save")
    class SaveTests {

        @Test
        @DisplayName("Deve salvar usuário com sucesso e criptografar a senha")
        void save_Success() throws Exception {
            User newUser = createValidUser(null, "Novo Usuário", "novo@utfpr.edu.br", "11111111111", null, "senha123");

            when(utilsService.getRoleType(anyString())).thenReturn(Type.ROLE_STUDENT);
            when(userRepository.findByEmail(newUser.getEmail())).thenReturn(null);
            when(userRepository.saveAndFlush(any(User.class))).thenAnswer(i -> {
                User userToSave = i.getArgument(0);
                userToSave.setId(1L);
                return userToSave;
            });

            when(userRepository.findById(1L)).thenReturn(Optional.of(newUser));

            UserBalance balance = new UserBalance();
            balance.setNegativeLimit(BigDecimal.valueOf(150.00));
            UserBalanceService balanceService = mock(UserBalanceService.class);
            when(balanceService.findByUser(any(User.class))).thenReturn(balance);

            try (MockedStatic<ApplicationContextProvider> context = mockStatic(ApplicationContextProvider.class)) {
                context.when(() -> ApplicationContextProvider.getBean(UserBalanceService.class)).thenReturn(balanceService);

                User savedUser = userService.save(newUser);

                assertThat(savedUser).isNotNull();
                assertThat(savedUser.getId()).isEqualTo(1L);
                assertThat(savedUser.getRole()).isEqualTo(Type.ROLE_STUDENT);
                assertThat(savedUser.getStatus()).isEqualTo(StatusInactiveActive.ACTIVE);

                assertThat(savedUser.getPassword()).isNotEqualTo("senha123");
                assertThat(passwordEncoder.matches("senha123", savedUser.getPassword())).isTrue();

                verify(emailConfigService, times(1)).validateIfExistsEmailConfig();
                verify(emailCodeService, times(1)).createCode(any(User.class));
            }
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar usuário com e-mail já existente")
        void save_EmailAlreadyExists_ShouldThrowException() {
            User newUser = createValidUser(null, "Novo", "existente@utfpr.edu.br", "11111111111", Type.ROLE_STUDENT, "senha123");
            User userDb = createValidUser(2L, "Antigo", "existente@utfpr.edu.br", "22222222222", Type.ROLE_STUDENT, "senhaAntiga");

            when(utilsService.getRoleType(anyString())).thenReturn(Type.ROLE_STUDENT);
            when(userRepository.findByEmail(newUser.getEmail())).thenReturn(userDb);

            assertThatThrownBy(() -> userService.save(newUser))
                    .isInstanceOf(GenericException.class)
                    .hasMessage("Email já cadastrado.");
        }
    }


    @Nested
    @DisplayName("Testes de findOneById")
    class FindOneByIdTests {

        @Test
        @DisplayName("Deve encontrar usuário por ID e preencher o saldo dinâmico")
        void findOneById_Success() throws Exception {
            User dbUser = createValidUser(1L, "Teste", "teste@utfpr.edu.br", "111", Type.ROLE_STUDENT, "senha");
            when(userRepository.findById(1L)).thenReturn(Optional.of(dbUser));

            UserBalance balance = new UserBalance();
            balance.setNegativeLimit(BigDecimal.valueOf(150.00));

            UserBalanceService balanceService = mock(UserBalanceService.class);
            when(balanceService.findByUser(dbUser)).thenReturn(balance);

            try (MockedStatic<ApplicationContextProvider> context = mockStatic(ApplicationContextProvider.class)) {
                context.when(() -> ApplicationContextProvider.getBean(UserBalanceService.class)).thenReturn(balanceService);

                User foundUser = userService.findOneById(1L);

                assertThat(foundUser).isNotNull();
                assertThat(foundUser.getBalance()).isEqualTo(BigDecimal.valueOf(150.00));
            }
        }
    }

    @Nested
    @DisplayName("Testes de sendEmailCodeRecoverPassword")
    class SendEmailCodeRecoverPasswordTests {

        @Test
        @DisplayName("Deve enviar código para o email de recuperação com sucesso")
        void sendEmailCodeRecoverPassword_Success() throws Exception {
            User user = createValidUser(1L, "Teste", "teste@utfpr.edu.br", "111", Type.ROLE_STUDENT, "senha");
            when(userRepository.findByEmail("teste@utfpr.edu.br")).thenReturn(user);

            SendEmailCodeRecoverPassword response = userService.sendEmailCodeRecoverPassword("teste@utfpr.edu.br");

            assertThat(response).isNotNull();
            assertThat(response.getMessage()).contains("Código enviado com sucesso para o e-mail");
            assertThat(response.getEmail()).isEqualTo("teste@utfpr.edu.br");

            verify(recoverPasswordService, times(1)).addCode(eq("teste@utfpr.edu.br"), any(RecoverPassword.class));
            verify(emailService, times(1)).sendEmail(any());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar enviar código para usuário não encontrado")
        void sendEmailCodeRecoverPassword_UserNotFound_ShouldThrowException() {
            when(userRepository.findByEmail("inexistente@utfpr.edu.br")).thenReturn(null);

            assertThatThrownBy(() -> userService.sendEmailCodeRecoverPassword("inexistente@utfpr.edu.br"))
                    .isInstanceOf(GenericException.class)
                    .hasMessage("Usuário não encontrado.");
        }
    }
}