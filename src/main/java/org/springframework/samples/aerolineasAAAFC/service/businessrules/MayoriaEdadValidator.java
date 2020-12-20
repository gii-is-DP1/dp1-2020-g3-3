package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MayoriaEdadValidator implements ConstraintValidator<MayoriaEdadConstraint, LocalDate>{

	@Override
	public void initialize(MayoriaEdadConstraint edad) {
	}
	
	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		
		Boolean valid = false;
		
		if(value.isBefore(LocalDate.now().minusYears(18))) valid = true;
		
		return valid;
	}

}
