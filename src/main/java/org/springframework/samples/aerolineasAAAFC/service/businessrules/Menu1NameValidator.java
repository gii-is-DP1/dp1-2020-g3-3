package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.utils.menuUtils;

public class Menu1NameValidator implements ConstraintValidator<Menu1AvailabilityConstraint, String>{

	@Override
	public void initialize(Menu1AvailabilityConstraint menuValue) {
	}

	
	@Override
	public boolean isValid(String menuField, ConstraintValidatorContext context) {
		return menuUtils.validarPrimerPlato(menuField);
	}

}
