package com.portal.centro.API.utils;

import com.portal.centro.API.user.Type;
import org.springframework.stereotype.Component;

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

}
