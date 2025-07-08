package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.configuration.ApplicationContextProvider;
import br.edu.utfpr.pb.app.labcaapi.dto.ChangePasswordDTO;
import br.edu.utfpr.pb.app.labcaapi.dto.EmailDto;
import br.edu.utfpr.pb.app.labcaapi.dto.RecoverPasswordDTO;
import br.edu.utfpr.pb.app.labcaapi.enums.StatusInactiveActive;
import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.RecoverPassword;
import br.edu.utfpr.pb.app.labcaapi.model.SendEmailCodeRecoverPassword;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.model.UserBalance;
import br.edu.utfpr.pb.app.labcaapi.repository.UserRepository;
import br.edu.utfpr.pb.app.labcaapi.responses.DefaultResponse;
import br.edu.utfpr.pb.app.labcaapi.utils.DateTimeUtil;
import br.edu.utfpr.pb.app.labcaapi.utils.UtilsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserService extends GenericService<User, Long> {

    BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UtilsService utilsService;
    private final RecoverPasswordService recoverPasswordService;
    private final EmailCodeService emailCodeService;
    private final EmailService emailService;
    private final EmailConfigService emailConfigService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UtilsService utilsService,
            EmailCodeService emailCodeService,
            RecoverPasswordService recoverPasswordService,
            EmailService emailService,
            EmailConfigService emailConfigService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.utilsService = utilsService;
        this.emailCodeService = emailCodeService;
        this.recoverPasswordService = recoverPasswordService;
        this.emailService = emailService;
        this.emailConfigService = emailConfigService;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public User save(User requestBody) throws Exception {
        emailConfigService.validateIfExistsEmailConfig();
        encryptPassword(requestBody);
        Type role = utilsService.getRoleType(requestBody.getEmail());
        requestBody.setPermissions(utilsService.getPermissionsByRole(role));
        requestBody.setRole(role);
        requestBody.setStatus(StatusInactiveActive.ACTIVE);
        requestBody.setType(requestBody.getType());
        requestBody.setCpfCnpj(requestBody.getCpfCnpj());
        this.validate(requestBody);
        User user = super.save(requestBody);
        this.emailCodeService.createCode(user);
        return user;
    }

    @Override
    @Transactional
    public User update(User requestBody) throws Exception {
        emailConfigService.validateIfExistsEmailConfig();
        User usuario = findOneById(requestBody.getId());
        usuario.setStatus(requestBody.getStatus());
        usuario.setType(requestBody.getType());
        usuario.setCpfCnpj(requestBody.getCpfCnpj());
        usuario.setRole(requestBody.getRole());
        usuario.setPermissions(utilsService.getPermissionsByRole(usuario.getRole()));
        usuario.setName(requestBody.getName());
        usuario.setRaSiape(requestBody.getRaSiape());
        this.validate(usuario);

        User selfUser = findSelfUser();
        if (Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            if (requestBody.getBalance() != null) {
                UserBalanceService balanceService = ApplicationContextProvider.getBean(UserBalanceService.class);
                UserBalance balance = balanceService.findByUser(usuario);
                balance.setNegativeLimit(requestBody.getBalance());
                balanceService.update(balance);
            }
        }
        return userRepository.save(usuario);
    }

    @Override
    @Transactional
    public User findOneById(Long aLong) throws Exception {
        User user = super.findOneById(aLong);
        UserBalance balance = ApplicationContextProvider.getBean(UserBalanceService.class).findByUser(user);
        user.setBalance(balance.getNegativeLimit());
        return user;
    }

    private void validate(User user) throws Exception {
        User userDb = this.userRepository.findByEmail(user.getEmail());
        if (userDb != null && !Objects.equals(userDb.getId(), user.getId())) {
            throw new GenericException("Email já cadastrado.");
        }
    }

    public SendEmailCodeRecoverPassword sendEmailCodeRecoverPassword(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user))
            throwExceptionUserNotFound();
        Integer code = new Random().nextInt(1000000);
        recoverPasswordService.addCode(email, new RecoverPassword(email, code, DateTimeUtil.getCurrentDateTime()));
        emailService.sendEmail(getEmailDtoToSendEmailWithCode(user.getEmail(), code));
        return new SendEmailCodeRecoverPassword("Código enviado com sucesso para o e-mail " + user.getEmail() + ".", user.getEmail());
    }

    private EmailDto getEmailDtoToSendEmailWithCode(String email, Integer code) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailTo(email);
        emailDto.setSubject("Recuperação de senha");
        emailDto.setSubjectBody("Recuperação de senha");
        emailDto.setContentBody("O código para recuperação da sua senha no sistema Portal CA é <b>" + code + "</b>.");
        return emailDto;
    }

    public DefaultResponse recoverPassword(RecoverPasswordDTO recoverPasswordDTO) throws Exception {
        User user = userRepository.findByEmail(recoverPasswordDTO.getEmail());
        if (Objects.isNull(user))
            throwExceptionUserNotFound();

        RecoverPassword recoverPassword = recoverPasswordService.getCodeSentByEmail().getOrDefault(recoverPasswordDTO.getEmail(), new RecoverPassword());
        Boolean codesMatch = Objects.equals(recoverPasswordDTO.getCode(), recoverPassword.getCode());
        if (!codesMatch) {
            throw new GenericException("Código inválido.");
        }

        updateUserNewPasswordByEmail(user, recoverPasswordDTO.getNewPassword());
        recoverPasswordService.getCodeSentByEmail().remove(recoverPasswordDTO.getEmail());
        return new DefaultResponse(HttpStatus.OK.value(), "Senha recuperada com sucesso.");
    }

    public DefaultResponse changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        User user = findSelfUser();
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new GenericException("Senha atual inválida.");
        } else {
            updateUserNewPasswordByEmail(user, changePasswordDTO.getNewPassword());
            return new DefaultResponse(HttpStatus.OK.value(), "Senha alterada com sucesso.");
        }

    }

    private void updateUserNewPasswordByEmail(User user, String newPassword) throws Exception {
        if (Objects.isNull(user))
            return;
        user.setPassword(newPassword);
        encryptPassword(user);
        userRepository.save(user);
    }

    private void encryptPassword(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    private void throwExceptionUserNotFound() throws Exception {
        throw new GenericException("Usuário não encontrado.");
    }

    public User findSelfUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.toString());
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> findUsersByRole(String role) throws Exception {
        Type type;
        try {
            type = Type.valueOf(role);
        } catch (Exception e) {
            throw new GenericException("Role informada não existe.");
        }
        return userRepository.findAllByRole(type);
    }

    public List<User> findUsersByDomain(String domain) throws Exception {
        return userRepository.findAllByEmailContainingIgnoreCase(domain);
    }

}
