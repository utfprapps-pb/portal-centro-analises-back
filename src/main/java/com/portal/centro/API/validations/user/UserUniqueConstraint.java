package com.portal.centro.API.validations.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserUniqueValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserUniqueConstraint {

    String message() default "O usuário já existe.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
