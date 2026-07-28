package br.edu.utfpr.pb.app.labcaapi;

import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User createValidUser(String name, String email, String cpfCnpj, Type role) {
        return User.builder()
                .name(name)
                .email(email)
                .password("123456")
                .cpfCnpj(cpfCnpj)
                .role(role)
                .status(StatusInactiveActive.ACTIVE)
                .build();
    }

    @Nested
    @DisplayName("Testes de findByEmail")
    class FindByEmailTests {

        @Test
        @DisplayName("Deve encontrar um usuário pelo e-mail com sucesso")
        void findByEmail_Success() {
            User user = createValidUser("Nome Teste", "teste@utfpr.edu.br", "12345678901", Type.ROLE_STUDENT);
            entityManager.persist(user);
            entityManager.flush();

            Optional<User> foundUserOpt = userRepository.findByEmail("teste@utfpr.edu.br");

            assertThat(foundUserOpt).isPresent(); // Check if optional has value

            User foundUser = foundUserOpt.get();
            assertThat(foundUser.getEmail()).isEqualTo("teste@utfpr.edu.br");
            assertThat(foundUser.getName()).isEqualTo("Nome Teste");
            assertThat(foundUser.getCpfCnpj()).isEqualTo("12345678901");
            assertThat(foundUser.getRole()).isEqualTo(Type.ROLE_STUDENT);
        }

        @Test
        @DisplayName("Deve retornar null quando o e-mail não existir na base de dados")
        void findByEmail_NotFound_ShouldReturnNull() {
            Optional<User> foundUserOpt = userRepository.findByEmail("inexistente@utfpr.edu.br");

            assertThat(foundUserOpt).isEmpty();
        }
    }

    @Nested
    @DisplayName("Testes de findAllByRole")
    class FindAllByRoleTests {

        @Test
        @DisplayName("Deve retornar apenas os usuários pertencentes ao papel (Type) informado")
        void findAllByRole_Success() {
            User student1 = createValidUser("Aluno 1", "aluno1@utfpr.edu.br", "11111111111", Type.ROLE_STUDENT);
            User student2 = createValidUser("Aluno 2", "aluno2@utfpr.edu.br", "22222222222", Type.ROLE_STUDENT);
            User admin = createValidUser("Admin", "admin@utfpr.edu.br", "33333333333", Type.ROLE_ADMIN);

            entityManager.persist(student1);
            entityManager.persist(student2);
            entityManager.persist(admin);
            entityManager.flush();

            List<User> students = userRepository.findAllByRole(Type.ROLE_STUDENT);

            assertThat(students)
                    .isNotNull()
                    .hasSize(2)
                    .extracting(User::getEmail)
                    .containsExactlyInAnyOrder("aluno1@utfpr.edu.br", "aluno2@utfpr.edu.br");

            List<User> admins = userRepository.findAllByRole(Type.ROLE_ADMIN);

            assertThat(admins)
                    .isNotNull()
                    .hasSize(1)
                    .extracting(User::getEmail)
                    .containsExactlyInAnyOrder("admin@utfpr.edu.br");
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia caso não existam usuários com o papel informado")
        void findAllByRole_WhenNoMatches_ShouldReturnEmptyList() {
            User student = createValidUser("Aluno", "aluno@utfpr.edu.br", "11111111111", Type.ROLE_STUDENT);
            entityManager.persist(student);
            entityManager.flush();

            List<User> admins = userRepository.findAllByRole(Type.ROLE_ADMIN);

            assertThat(admins).isNotNull().isEmpty();
        }
    }

    @Nested
    @DisplayName("Testes de findAllByEmailContainingIgnoreCase")
    class FindAllByEmailContainingIgnoreCaseTests {

        @Test
        @DisplayName("Deve filtrar usuários pelo trecho do e-mail ignorando maiúsculas e minúsculas")
        void findAllByEmailContainingIgnoreCase_Success() {
            User user1 = createValidUser("João", "joao@utfpr.edu.br", "11111111111", Type.ROLE_STUDENT);
            User user2 = createValidUser("Maria", "maria@UTFPR.EDU.BR", "22222222222", Type.ROLE_STUDENT);
            User user3 = createValidUser("Carlos", "carlos@gmail.com", "33333333333", Type.ROLE_STUDENT);

            entityManager.persist(user1);
            entityManager.persist(user2);
            entityManager.persist(user3);
            entityManager.flush();

            List<User> utfprUsers = userRepository.findAllByEmailContainingIgnoreCase("UtfPr");

            assertThat(utfprUsers)
                    .hasSize(2)
                    .extracting(User::getEmail)
                    .containsExactlyInAnyOrder("joao@utfpr.edu.br", "maria@UTFPR.EDU.BR");
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia quando nenhum e-mail contiver o termo pesquisado")
        void findAllByEmailContainingIgnoreCase_WhenNoMatches_ShouldReturnEmptyList() {
            User user = createValidUser("João", "joao@utfpr.edu.br", "11111111111", Type.ROLE_STUDENT);
            entityManager.persist(user);
            entityManager.flush();

            List<User> results = userRepository.findAllByEmailContainingIgnoreCase("gmail.com");

            assertThat(results).isNotNull().isEmpty();
        }
    }

    @Nested
    @DisplayName("Testes de Ciclo de Vida da Entidade")
    class EntityLifecycleTests {

        @Test
        @DisplayName("Deve preencher automaticamente o campo createdAt ao persistir um usuário")
        void prePersist_ShouldPopulateCreatedAt() {
            User user = createValidUser("Novo Usuário", "novo@utfpr.edu.br", "99999999999", Type.ROLE_STUDENT);

            User savedUser = entityManager.persistAndFlush(user);

            assertThat(savedUser.getCreatedAt()).isNotNull();
        }
    }
}
