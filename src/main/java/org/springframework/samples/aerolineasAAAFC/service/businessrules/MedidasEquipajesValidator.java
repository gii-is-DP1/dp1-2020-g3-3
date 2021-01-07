package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MedidasEquipajesValidator implements ConstraintValidator<MedidasEquipajesConstraint, String>{

	@Override
	public void initialize(MedidasEquipajesConstraint medidasValue) {
	}
	
	@Override
	public boolean isValid(String medidasValue, ConstraintValidatorContext context) {
		
		Boolean cond = false;

		switch (medidasValue) {
		case "110x110x78":
			cond = true;
			break;
		case "60x60x42":
			cond = true;
			break;
		case "36x36x26":
			cond = true;
			break;
		}
			
		return cond;
	}

}
