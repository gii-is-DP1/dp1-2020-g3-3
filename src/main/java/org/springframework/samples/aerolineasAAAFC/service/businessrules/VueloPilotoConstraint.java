package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = VueloPilotoValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface VueloPilotoConstraint {
	String message() default "Un vuelo de menos de 8 horas debe tener 2 pilotos, en caso de ser más de 8 horas, tendrá un mínimo de 3";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
