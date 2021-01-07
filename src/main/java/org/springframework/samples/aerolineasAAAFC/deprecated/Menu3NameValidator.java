package org.springframework.samples.aerolineasAAAFC.deprecated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Menu3NameValidator implements ConstraintValidator<Menu3AvailabilityConstraint, String>{

	@Override
	public void initialize(Menu3AvailabilityConstraint menuValue) {
	}

	
	@Override
	public boolean isValid(String menuField, ConstraintValidatorContext context) {
		return menuUtils.validarPostres(menuField);
	}

}
