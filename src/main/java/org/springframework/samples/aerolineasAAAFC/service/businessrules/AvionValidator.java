package org.springframework.samples.aerolineasAAAFC.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.Avion;

public class AvionValidator implements 
ConstraintValidator<AvionConstraint, Avion>{

	@Override
	public void initialize(AvionConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(Avion avion, ConstraintValidatorContext context) {
			boolean res;
			
			int capacidadDeAvion = avion.getCapacidadPasajero();
			int plazasEconomicas = avion.getPlazasEconomica();
			int plazasEjecutivas = avion.getPlazasEjecutiva();
			int plazasPrimera = avion.getPlazasPrimera();
			
			int sumPlazas = plazasEconomicas + plazasEjecutivas + plazasPrimera;
			
			res = sumPlazas == capacidadDeAvion? true : false;

			return res;
	}

}
