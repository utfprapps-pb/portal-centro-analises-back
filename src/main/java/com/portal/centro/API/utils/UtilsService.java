package com.portal.centro.API.utils;

import com.portal.centro.API.enums.Action;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.model.DomainRole;
import com.portal.centro.API.model.Permission;
import com.portal.centro.API.repository.DomainRoleRepository;
import com.portal.centro.API.repository.PermissionRepository;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UtilsService {

    private final PermissionRepository permissionRepository;
    private final DomainRoleRepository domainRoleRepository;

    public UtilsService(PermissionRepository permissionRepository, DomainRoleRepository domainRoleRepository) {
        this.permissionRepository = permissionRepository;
        this.domainRoleRepository = domainRoleRepository;
    }

    public String generateEmailMessage(String content) {
        return "";
    }

    public Type getRoleType(String email) {
        List<DomainRole> validDomainRoles = domainRoleRepository.findAll();
        try {
            //InternetAddress internetAddress = new InternetAddress(email);
            //internetAddress.validate(); //Valida o formato do email usando as especificações RFC 5322
            String dominio = email.split("@")[1]; /*Separa o endereço de email em duas partes:
                                                                                o nome do usuário e o domínio, separados
                                                                                pelo caractere "@". Acessando a segunda
                                                                                String do array com [1]  */
            Optional<DomainRole> domainRoleOptional =
                    validDomainRoles
                            .stream()
                            .filter((domain) -> Objects.equals(domain.getDomain(), dominio))
                            .findFirst();
            if (domainRoleOptional.isPresent())
                return domainRoleOptional.get().getRole();
            else
                return Type.ROLE_EXTERNAL;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao configurar privilégio do usuário pelo domínio do e-mail. " +
                            "Detalhes: " + e.getMessage()
            );
        }
    }

    public List<Permission> getPermissionsByRole(final Type role) {
        List<Permission> permissionList = permissionRepository.findAll();

        if(Type.ROLE_STUDENT.equals(role)) {
            return permissionList.stream().filter(permission -> permission.getAction().equals(Action.READ)).toList();
        } else if (Type.ROLE_PROFESSOR.equals(role)) {
            return permissionList;
        } else {
            return permissionList.stream().filter(permission -> permission.getAction().equals(Action.CREATE) || permission.getAction().equals(Action.READ)).toList();
        }
    }

    public Collection<InternetAddress> getEmailToSend(final String email) throws AddressException {
        Collection<InternetAddress> addresses = new ArrayList<>();
        InternetAddress emailAddress = new InternetAddress(email);
        addresses.add(emailAddress);
        return addresses;
    }

}
