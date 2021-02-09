package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Documented
@Constraint(validatedBy = MedidasEquipajesValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MedidasEquipajesConstraint {
	String message() default "Las medidas del equipaje deben ser las definidas por la página";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}