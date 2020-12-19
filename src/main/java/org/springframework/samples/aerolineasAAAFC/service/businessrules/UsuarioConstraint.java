package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UsuarioValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioConstraint {
	String message() default "El nif y el usuario deben ser iguales";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String[] value();
}
