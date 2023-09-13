package com.portal.centro.API.utils;

import com.portal.centro.API.enums.Action;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.model.DomainRole;
import com.portal.centro.API.model.Permission;
import com.portal.centro.API.repository.DomainRoleRepository;
import com.portal.centro.API.repository.PermissionRepository;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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

    public Type getRoleType(String email){
//        String[] dominiosValidos = {"professores.utfpr.edu.br", "utfpr.edu.br", "alunos.utfpr.edu.br"}; //Permitidos
        List<DomainRole> validDomainRoles = domainRoleRepository.findAll();
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate(); //Valida o formato do email usando as especificações RFC 5322
            String dominio = internetAddress.getAddress().split("@")[1]; /*Separa o endereço de email em duas partes:
                                                                                o nome do usuário e o domínio, separados
                                                                                pelo caractere "@". Acessando a segunda
                                                                                String do array com [1]  */
//            for (DomainRole dominioValido : validDomainRoles) {
//                if (dominio.equals(dominioValido.getDomain())) {
//                    if ("professores.utfpr.edu.br".equals(dominio) || "utfpr.edu.br".equals(dominio)){
//                        return Type.PROFESSOR;
//                    }  else {
//                       return Type.STUDENT;
//                    }
//                }
//            }
            Optional<DomainRole> domainRoleOptional =
                    validDomainRoles
                            .stream()
                            .filter((domain) -> Objects.equals(domain.getDomain(), dominio))
                            .findFirst();
            if (domainRoleOptional.isPresent())
                return domainRoleOptional.get().getRole();
            return Type.EXTERNAL;
        } catch (Exception e) {
            return Type.EXTERNAL;
        }
    }

    public List<Permission> getPermissionsByRole(final Type role) {
        List<Permission> permissionList = permissionRepository.findAll();

        if(Type.STUDENT.equals(role)) {
            return permissionList.stream().filter(permission -> permission.getAction().equals(Action.READ)).toList();
        } else if (Type.PROFESSOR.equals(role)) {
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
