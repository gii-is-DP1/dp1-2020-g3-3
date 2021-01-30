package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches { 
    String message() default "Las contraseñas no coinciden";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}