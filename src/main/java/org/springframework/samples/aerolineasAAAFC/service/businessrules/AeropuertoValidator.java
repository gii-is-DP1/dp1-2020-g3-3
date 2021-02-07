package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.Vuelo;

public class AeropuertoValidator implements 
ConstraintValidator<AeropuertoConstraint, Vuelo>{

	@Override
	public void initialize(AeropuertoConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(Vuelo vuelo, ConstraintValidatorContext context) {
			String origen = vuelo.getAeropuertoOrigen().getCodigoIATA();
			
			String destino = vuelo.getAeropuertoDestino().getCodigoIATA();
			
			boolean res= true;
			
			if(origen.equals(destino)) {res=false;}

			return res;
	}

}
