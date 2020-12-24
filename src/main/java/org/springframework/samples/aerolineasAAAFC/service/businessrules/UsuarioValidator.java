package org.springframework.samples.aerolineasAAAFC.service.businessrules;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.Person;
import org.springframework.samples.aerolineasAAAFC.model.User;

import com.sun.jmx.mbeanserver.Introspector;

public class UsuarioValidator implements ConstraintValidator<UsuarioConstraint, Person>{


	@Override
	public void initialize(UsuarioConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(Person value, ConstraintValidatorContext context) {


		String nif = value.getNif();
		String usuario =  value.getUser().getUsername();

		return nif.equals(usuario);


	}

}
