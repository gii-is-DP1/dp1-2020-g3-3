package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.utils.menuUtils;

public class Menu3NameValidator implements ConstraintValidator<Menu3AvailabilityConstraint, String>{

	@Override
	public void initialize(Menu3AvailabilityConstraint menuValue) {
	}

	
	@Override
	public boolean isValid(String menuField, ConstraintValidatorContext context) {
		return menuUtils.validarPostres(menuField);
	}

}
