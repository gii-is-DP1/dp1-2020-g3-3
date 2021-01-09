package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = VueloTCPValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface VueloTCPConstraint {
	String message() default "Para todos los aviones con más de 19 asientos es obligatorio que cuenten con un tripulante de cabina de pasajeros (TCP) para poder operar."
							+ " A partir de 50 asientos deben de ir 2 TCP a bordo; por cada bloque de 50 asientos adicionales, sumaremos un TCP más.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
