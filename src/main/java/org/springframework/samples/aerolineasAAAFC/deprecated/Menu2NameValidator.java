package org.springframework.samples.aerolineasAAAFC.deprecated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Menu2NameValidator implements ConstraintValidator<Menu2AvailabilityConstraint, String>{

	@Override
	public void initialize(Menu2AvailabilityConstraint menuValue) {
	}

	
	@Override
	public boolean isValid(String menuField, ConstraintValidatorContext context) {
		return menuUtils.validarSegundoPlato(menuField);
	}

}
