package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;

import com.sun.jmx.mbeanserver.Introspector;

public class VueloTCPValidator implements 
ConstraintValidator<VueloTCPConstraint, Vuelo>{


	@Override
	public void initialize(VueloTCPConstraint constraintAnnotation) {
	}


	@Override
	public boolean isValid(Vuelo vuelo,ConstraintValidatorContext context) {
		
			Set<Azafato> azafatosSet = vuelo.getAzafatos();
			Integer asientos = vuelo.getAvion().getCapacidadPasajero();
			int azafatos = azafatosSet.size();
			
			boolean res= true;

			if(asientos >= 50) {
				int i = 1;
				int aux = asientos/50;
				if(azafatos < i+aux) res = false;
			}else {
				if(azafatos < 1) res = false;
			}

			return res;

		
	}

}
