package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;

import com.sun.jmx.mbeanserver.Introspector;

public class VueloPilotoValidator implements 
ConstraintValidator<VueloPilotoConstraint, Vuelo>{


	@Override
	public void initialize(VueloPilotoConstraint constraintAnnotation) {
	}


	@Override
	public boolean isValid(Vuelo vuelo,ConstraintValidatorContext context) {
		
		
			LocalDateTime horaSalida = vuelo.getFechaSalida();

			LocalDateTime horaLlegada = vuelo.getFechaLlegada();

			long horas = horaSalida.until(horaLlegada, ChronoUnit.HOURS);

			Set<PersonalControl> pControl = vuelo.getPersonalControl();

			Integer pilotos = 0;

			for(PersonalControl p: pControl) {
				if(p.getRol() == Rol.PILOTO) pilotos++;
			}

			boolean res= true;

			if(horas <= 8 && pilotos < 2) res = false;
			else if (horas > 8 && pilotos < 3) res = false;

			return res;

		
	}

}
