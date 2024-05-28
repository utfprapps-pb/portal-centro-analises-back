package com.portal.centro.API.service;

import com.portal.centro.API.dto.ChangePasswordDTO;
import com.portal.centro.API.dto.EmailDto;
import com.portal.centro.API.dto.RecoverPasswordDTO;
import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.enums.TransactionType;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.*;
import com.portal.centro.API.repository.ProjectRepository;
import com.portal.centro.API.repository.UserRepository;
import com.portal.centro.API.responses.DefaultResponse;
import com.portal.centro.API.utils.DateTimeUtil;
import com.portal.centro.API.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.portal.centro.API.enums.Type.ROLE_ADMIN;

@Service
public class UserService extends GenericService<User, Long> {

    BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UtilsService utilsService;
    private final RecoverPasswordService recoverPasswordService;
    private final EmailCodeService emailCodeService;
    private final EmailService emailService;
    private final ProjectRepository projectRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UtilsService utilsService,
            EmailCodeService emailCodeService,
            RecoverPasswordService recoverPasswordService,
            EmailService emailService,
            ProjectRepository projectRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.utilsService = utilsService;
        this.emailCodeService = emailCodeService;
        this.recoverPasswordService = recoverPasswordService;
        this.emailService = emailService;
        this.projectRepository = projectRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User save(User requestBody) throws Exception {
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
    public User update(User requestBody) throws Exception {
        User usuario = findOneById(requestBody.getId());
        usuario.setStatus(requestBody.getStatus());
        usuario.setType(requestBody.getType());
        usuario.setCpfCnpj(requestBody.getCpfCnpj());
        usuario.setRole(requestBody.getRole());
        usuario.setPermissions(utilsService.getPermissionsByRole(usuario.getRole()));
        usuario.setName(requestBody.getName());
        usuario.setRaSiape(requestBody.getRaSiape());
        this.validate(usuario);
        return super.update(usuario);
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
        super.save(user);
    }

    private void encryptPassword(User entity) {
        if (entity.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
    }

    private void throwExceptionUserNotFound() throws Exception {
        throw new GenericException("Usuário não encontrado.");
    }

    public User findSelfUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(principal.toString());
        return user;
    }

    public User findByEmail(@PathVariable("email") String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> findUsersByRole(@PathVariable("role") String role) throws Exception {
        Type type;
        try {
            type = Type.valueOf(role);
        } catch (Exception e) {
            throw new GenericException("Role informada não existe.");
        }
        return userRepository.findAllByRole(type);
    }

    public UserBalance updateBalance(Long userId, TransactionType transactionType, BigDecimal value) throws Exception {
        User user = findOneById(userId);
        if (Objects.isNull(user))
            throwExceptionUserNotFound();
        BigDecimal balance = Objects.nonNull(user.getBalance()) ? user.getBalance() : BigDecimal.ZERO;
        UserBalance userBalance = new UserBalance();
        userBalance.setOld(balance);
        switch (transactionType) {
            case DEPOSIT -> balance = balance.add(value);
            case WITHDRAW -> balance = balance.subtract(value);
        }
        userBalance.setCurrent(balance);
        user.setBalance(balance);
        userRepository.save(user);
        return userBalance;
    }


}
