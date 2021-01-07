package org.springframework.samples.aerolineasAAAFC.deprecated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Menu1NameValidator implements ConstraintValidator<Menu1AvailabilityConstraint, String>{

	@Override
	public void initialize(Menu1AvailabilityConstraint menuValue) {
	}

	
	@Override
	public boolean isValid(String menuField, ConstraintValidatorContext context) {
		return menuUtils.validarPrimerPlato(menuField);
	}

}
