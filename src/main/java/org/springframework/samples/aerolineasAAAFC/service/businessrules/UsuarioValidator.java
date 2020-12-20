package org.springframework.samples.aerolineasAAAFC.service.businessrules;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.User;

import com.sun.jmx.mbeanserver.Introspector;

public class UsuarioValidator implements ConstraintValidator<UsuarioConstraint, Object>{

	private String firstAttribute ;
	private String secondAttribute ;
	
	@Override
	public void initialize(final UsuarioConstraint constraintAnnotation) {
		firstAttribute  = constraintAnnotation.value()[0];
		secondAttribute  = constraintAnnotation.value()[1];
	}

	@SuppressWarnings("restriction")
	@Override
	public boolean isValid(final Object value, ConstraintValidatorContext context) {
		try {

			String nif = (String) Introspector.elementFromComplex(value, firstAttribute);
			User usuario =  (User) Introspector.elementFromComplex(value,secondAttribute);

			return nif.equals(usuario.getUsername());

		}catch(final Exception e) {
			throw new IllegalArgumentException(e);
		}

	}

}
