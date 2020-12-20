package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NifValidator implements ConstraintValidator<NifConstraint, String>{


	private Pattern reg =  Pattern.compile("[0-9]{8,8}[A-Z]");
	
	@Override
	public void initialize(NifConstraint constraint) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		final Matcher matcher = reg.matcher(value);

		if(!matcher.matches()){
			return false;
		}

		final String nums = value.substring(0,8);
		final String letra = value.substring(8,9);
		final String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		final int posicion = Integer.parseInt(nums)%23;

		final String letraCalculada = letras.substring(posicion,posicion+1);

		if(!letra.equalsIgnoreCase(letraCalculada)){
			return false;
		}
		return true;
	}

}
