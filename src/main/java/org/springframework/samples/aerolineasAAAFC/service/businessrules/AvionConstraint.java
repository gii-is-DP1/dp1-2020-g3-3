package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = AvionValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvionConstraint {
	String message() default "Las plazas por clase del avión, deben corresponder a la capacidad del mismo.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}