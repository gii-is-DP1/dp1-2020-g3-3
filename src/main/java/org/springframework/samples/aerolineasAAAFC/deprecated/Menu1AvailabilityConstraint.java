package org.springframework.samples.aerolineasAAAFC.deprecated;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Documented
@Constraint(validatedBy = Menu1NameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Menu1AvailabilityConstraint {
	String message() default "El primer plato no está disponible";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
