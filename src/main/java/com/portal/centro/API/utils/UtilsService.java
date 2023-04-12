package com.portal.centro.API.utils;

import com.portal.centro.API.model.Permission;
import com.portal.centro.API.enums.Type;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Component
public class UtilsService {

    public Type getRoleType(String email){
        String substr = email.substring(email.indexOf("@") + 1, email.indexOf('.'));

        if("alunos".equals(substr)) {
            return Type.STUDENT;
        } else if ( "professores".equals(substr)) {
            return Type.PROFESSOR;
        }

        return Type.EXTERNAL;
    }

    public List<Permission> getPermissionsByRole(final Type role) {
        return null;
    }

}
