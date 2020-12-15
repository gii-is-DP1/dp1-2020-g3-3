package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import com.sun.jmx.mbeanserver.Introspector;

public class AeropuertoValidator implements 
ConstraintValidator<AeropuertoConstraint, Object>{

	private String firstAttribute ;
	private String secondAttribute ;

	@Override
	public void initialize(final AeropuertoConstraint constraintAnnotation) {
		firstAttribute  = constraintAnnotation.value()[0];
		secondAttribute  = constraintAnnotation.value()[1];

	}

	@SuppressWarnings("restriction")
	@Override
	public boolean isValid(final Object object,final ConstraintValidatorContext context) {
		try {
			Aeropuerto origen = (Aeropuerto) Introspector.elementFromComplex(object, firstAttribute);
			
			Aeropuerto destino =(Aeropuerto) Introspector.elementFromComplex(object,
					secondAttribute);
			
			boolean res= true;
			
			if(origen.equals(destino)) {res=false;}

			return res;
			
		} catch (final Exception e) {
			throw new IllegalArgumentException(e);
		}


	}

}
