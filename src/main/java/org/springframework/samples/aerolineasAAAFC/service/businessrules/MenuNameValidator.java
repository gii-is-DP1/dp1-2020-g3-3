package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.utils.menuUtils;

public class MenuNameValidator implements ConstraintValidator<MenuAvailabilityConstraint, String>{

	@Override
	public void initialize(MenuAvailabilityConstraint menuValue) {
	}

	
	@Override
	public boolean isValid(String menuField, ConstraintValidatorContext context) {
		return menuUtils.validaPlatos();
	}

}
